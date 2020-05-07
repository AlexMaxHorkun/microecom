package com.microecom.inventoryservice.model.storage.stock;

import com.microecom.inventoryservice.model.data.CalculatedAvailable;
import com.microecom.inventoryservice.model.data.Stock;
import com.microecom.inventoryservice.model.data.StockUpdate;
import com.microecom.inventoryservice.model.storage.StockRepository;
import com.microecom.inventoryservice.model.storage.stock.data.Calculated;
import com.microecom.inventoryservice.model.storage.stock.data.ExistingStock;
import com.microecom.inventoryservice.model.storage.stock.data.StockRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaStockRepository implements StockRepository {
    private final StockCrudRepository repo;

    public JpaStockRepository(@Autowired StockCrudRepository repo) {
        this.repo = repo;
    }

    @Override
    public Stock create(Stock stock) throws IllegalArgumentException {
        var existing = repo.findById(UUID.fromString(stock.getProductId()));
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Stock data already exists");
        }
        StockRow saved;
        try {
            saved = repo.save(new StockRow(UUID.fromString(stock.getProductId()), stock.getAvailable()));
        } catch (Exception exception) {
            throw new IllegalArgumentException("Invalid product ID provided", exception);
        }
        return convert(saved);
    }

    @Transactional(rollbackOn = {Exception.class})
    @Override
    public Stock update(StockUpdate update) throws IllegalArgumentException {
        var found = repo.findLockedById(UUID.fromString(update.getForProductId()));
        if (found.isEmpty()) {
            throw new IllegalArgumentException("Stock for given product does not exist");
        }

        var row = found.get();
        row.setAvailable(update.getAvailable().get());

        return convert(repo.save(row));
    }

    @Override
    public Iterable<CalculatedAvailable> calculateAvailableFor(List<String> productIds) {
        var ids = new HashSet<UUID>();
        for (String id : productIds) {
            ids.add(UUID.fromString(id));
        }
        var found = repo.findAllById(ids);
        var converted = new HashSet<CalculatedAvailable>();
        for (StockRow row : found) {
            converted.add(new Calculated(row.getProductId().toString(), row.getAvailable()));
        }

        return converted;
    }

    private ExistingStock convert(StockRow stock) {
        return new ExistingStock(stock.getProductId().toString(), stock.getAvailable());
    }
}
