package com.microecom.inventoryservice.model.stock;

import com.microecom.inventoryservice.eventlist.StockChangedEvent;
import com.microecom.inventoryservice.model.EventPublisher;
import com.microecom.inventoryservice.model.StockManager;
import com.microecom.inventoryservice.model.data.*;
import com.microecom.inventoryservice.model.exception.NoStockFoundException;
import com.microecom.inventoryservice.model.storage.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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
        if (update.getAvailable().isEmpty() && update.getSubAvailable().isEmpty()) {
            throw new IllegalArgumentException("Available/sub-available fields are required for an update");
        }

        var updated = repo.update(update);
        publisher.publish(
                new Event("stock-changed", new StockChangedEvent(updated.getProductId(), updated.getAvailable()))
        );

        return updated;
    }

    @Override
    public Set<Stock> findByProductIds(Set<String> productIds) throws NoStockFoundException {
        var found = repo.findByProductIds(productIds);

        return found;
    }
}
