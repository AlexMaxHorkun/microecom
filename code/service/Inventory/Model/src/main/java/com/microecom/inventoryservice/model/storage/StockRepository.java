package com.microecom.inventoryservice.model.storage;

import com.microecom.inventoryservice.model.data.CalculatedAvailable;
import com.microecom.inventoryservice.model.data.Stock;
import com.microecom.inventoryservice.model.data.StockUpdate;

import java.util.List;

public interface StockRepository {
    Stock create(Stock stock) throws IllegalArgumentException;

    Stock update(StockUpdate update) throws IllegalArgumentException;

    Iterable<CalculatedAvailable> calculateAvailableFor(List<String> productIds);
}
