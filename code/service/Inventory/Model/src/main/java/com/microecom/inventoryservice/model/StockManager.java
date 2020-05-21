package com.microecom.inventoryservice.model;

import com.microecom.inventoryservice.model.data.CalculatedAvailable;
import com.microecom.inventoryservice.model.data.Stock;
import com.microecom.inventoryservice.model.data.StockUpdate;
import com.microecom.inventoryservice.model.exception.NoStockFoundException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Manages stocks.
 */
public interface StockManager {
    Stock create(Stock stock) throws IllegalArgumentException;

    Stock update(StockUpdate update) throws IllegalArgumentException;

    Set<Stock> findByProductIds(Set<String> productIds) throws NoStockFoundException;
}
