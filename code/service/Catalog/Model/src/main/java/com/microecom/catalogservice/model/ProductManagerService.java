package com.microecom.catalogservice.model;

import com.microecom.catalogservice.model.client.InventoryClient;
import com.microecom.catalogservice.model.client.data.Stock;
import com.microecom.catalogservice.model.data.ExistingProduct;
import com.microecom.catalogservice.model.data.ProductInfo;
import com.microecom.catalogservice.model.data.ProductListCriteria;
import com.microecom.catalogservice.model.data.ProductUpdate;
import com.microecom.catalogservice.model.storage.ProductRepository;
import com.microecom.catalogservice.model.storage.data.ProductAvailabilityUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductManagerService implements ProductManager {
    private final ProductRepository repo;

    private final InventoryClient inventoryClient;

    public ProductManagerService(@Autowired ProductRepository repo, @Autowired InventoryClient inventoryClient) {
        this.repo = repo;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public ExistingProduct create(ProductInfo newProduct) throws IllegalArgumentException {
        return repo.create(newProduct);
    }

    @Override
    public ExistingProduct update(ProductUpdate update) throws IllegalArgumentException {
        return repo.update(update);
    }

    @Override
    public void delete(String id) throws IllegalArgumentException {
        repo.delete(id);
    }

    @Override
    public List<ExistingProduct> findList(ProductListCriteria criteria) throws IllegalArgumentException {
        if (criteria.getAvailable().isPresent()) {
            updateMissingAvailability();
        }

        return repo.findAll(criteria);
    }

    @Transactional
    @Override
    public synchronized Set<String> updateMissingAvailability() {
        var missing = repo.findAllIdsForUpdate(new ProductListCriteria(null, true));
        if (!missing.iterator().hasNext()) {
            return new HashSet<>();
        }

        var missingSet = new HashSet<String>();
        missing.forEach(missingSet::add);
        var stocks = inventoryClient.retrieveCalculatedStock(missingSet);
        var updates = new HashSet<ProductAvailabilityUpdate>();
        var updated = new HashSet<String>();
        for (Stock stock : stocks.values()) {
            updates.add(new ProductAvailabilityUpdate(stock.getProductId(), stock.getAvailable() > 0));
            updated.add(stock.getProductId());
        }
        repo.updateAvailability(updates);

        return updated;
    }
}
