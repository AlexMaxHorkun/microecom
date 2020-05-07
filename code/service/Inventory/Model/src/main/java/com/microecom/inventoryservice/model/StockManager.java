package com.microecom.inventoryservice.model;

import com.microecom.inventoryservice.model.data.CalculatedAvailable;
import com.microecom.inventoryservice.model.data.Stock;
import com.microecom.inventoryservice.model.data.StockUpdate;

import java.util.List;
import java.util.Map;

/**
 * Manages stocks.
 */
public interface StockManager {
    Stock create(Stock stock) throws IllegalArgumentException;

    Stock update(StockUpdate update) throws IllegalArgumentException;

    Map<String, CalculatedAvailable> calculateAvailableFor(List<String> productIds) throws IllegalArgumentException;
}
