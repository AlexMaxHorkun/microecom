package com.microecom.catalogservice.model.event.inventory;

import com.microecom.catalogservice.model.ProductManager;
import com.microecom.catalogservice.model.event.InventoryListener;
import com.microecom.catalogservice.model.exception.ProductNotFoundException;
import com.microecom.catalogservice.model.storage.ProductRepository;
import com.microecom.catalogservice.model.storage.data.ProductAvailabilityUpdate;
import com.microecom.inventoryservice.eventlist.StockChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class Listener implements InventoryListener {
    private final ProductManager products;

    private final ProductRepository repo;

    public Listener(@Autowired ProductManager products, @Autowired ProductRepository repo) {
        this.products = products;
        this.repo = repo;
    }

    @Override
    public void consumeStockChanged(StockChangedEvent event) throws ProductNotFoundException {
        var found = products.findById(event.getProductId());
        if (found.isEmpty()) {
            throw new ProductNotFoundException();
        }
        var available = new HashSet<ProductAvailabilityUpdate>();
        available.add(new ProductAvailabilityUpdate(event.getProductId(), event.getAvailable() > 0));
        repo.updateAvailability(available);
    }
}
