package com.microecom.inventoryservice.model.stock;

import com.microecom.inventoryservice.model.EventPublisher;
import com.microecom.inventoryservice.model.StockManager;
import com.microecom.inventoryservice.model.data.*;
import com.microecom.inventoryservice.model.storage.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockManagerService implements StockManager {
    private final StockRepository repo;

    private final EventPublisher publisher;

    public StockManagerService(@Autowired StockRepository repo, @Autowired EventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    @Override
    public Stock create(Stock stock) throws IllegalArgumentException {
        var created = repo.create(stock);
        publisher.publish(
                new Event("stock-changed", new StockChangedEvent(created.getProductId(), created.getAvailable()))
        );

        return created;
    }

    @Override
    public Stock update(StockUpdate update) throws IllegalArgumentException {
        if (update.getAvailable().isEmpty()) {
            throw new IllegalArgumentException("Available field is required for an update");
        }

        var updated = repo.update(update);
        publisher.publish(
                new Event("stock-changed", new StockChangedEvent(updated.getProductId(), updated.getAvailable()))
        );

        return updated;
    }

    @Override
    public Map<String, CalculatedAvailable> calculateAvailableFor(List<String> productIds) throws IllegalArgumentException {
        var data = repo.calculateAvailableFor(productIds);
        var map = new HashMap<String, CalculatedAvailable>();
        for (CalculatedAvailable datum : data) {
            map.put(datum.getForProductId(), datum);
        }

        return map;
    }
}
