package com.microecom.inventoryservice.model;

import com.microecom.inventoryservice.model.data.CalculatedAvailable;
import com.microecom.inventoryservice.model.exception.NoStockFoundException;

import java.util.Map;
import java.util.Set;

/**
 * Calculates available stock for products.
 */
public interface StockCalculator {
    Map<String, CalculatedAvailable> calculateAvailableFor(Set<String> productIds) throws NoStockFoundException;
}
