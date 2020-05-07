package com.microecom.inventoryservice.model.stock;

import com.microecom.inventoryservice.model.StockManager;
import com.microecom.inventoryservice.model.data.CalculatedAvailable;
import com.microecom.inventoryservice.model.data.Stock;
import com.microecom.inventoryservice.model.data.StockUpdate;
import com.microecom.inventoryservice.model.storage.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockManagerService implements StockManager {
    private final StockRepository repo;

    public StockManagerService(@Autowired StockRepository repo) {
        this.repo = repo;
    }

    @Override
    public Stock create(Stock stock) throws IllegalArgumentException {
        return repo.create(stock);
    }

    @Override
    public Stock update(StockUpdate update) throws IllegalArgumentException {
        if (update.getAvailable().isEmpty()) {
            throw new IllegalArgumentException("Available field is required for an update");
        }

        return repo.update(update);
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
