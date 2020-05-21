package com.microecom.inventoryservice.model.storage.stock;

import com.microecom.inventoryservice.model.data.Stock;
import com.microecom.inventoryservice.model.data.StockUpdate;
import com.microecom.inventoryservice.model.exception.NoStockFoundException;
import com.microecom.inventoryservice.model.storage.StockRepository;
import com.microecom.inventoryservice.model.storage.stock.data.ExistingStock;
import com.microecom.inventoryservice.model.storage.stock.data.StockRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

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
            throw new NoStockFoundException(update.getForProductId());
        }

        var row = found.get();
        if (update.getAvailable().isPresent()) {
            row.setAvailable(update.getAvailable().get());
        }
        if (update.getSubAvailable().isPresent()) {
            row.setAvailable(row.getAvailable() - update.getSubAvailable().get());
        }

        return convert(repo.save(row));
    }

    @Override
    public Set<Stock> findByProductIds(Set<String> ids) {
        var found = repo.findAllById(ids.stream().map(UUID::fromString).collect(Collectors.toSet()));
        var converted = new HashSet<Stock>();
        for (StockRow r : found) {
            converted.add(convert(r));
        }

        return converted;
    }

    @Override
    public Optional<Stock> findByProductId(String id) {
        return repo.findById(UUID.fromString(id)).map(this::convert);
    }

    private ExistingStock convert(StockRow stock) {
        return new ExistingStock(stock.getProductId().toString(), stock.getAvailable());
    }
}
