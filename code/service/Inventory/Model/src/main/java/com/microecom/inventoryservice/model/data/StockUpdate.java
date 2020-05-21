package com.microecom.inventoryservice.model.data;

import java.util.Optional;

/**
 * Update for stock data.
 */
public interface StockUpdate {
    String getForProductId();

    Optional<Integer> getAvailable();

    Optional<Integer> getSubAvailable();
}
