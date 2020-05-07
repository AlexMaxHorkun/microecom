package com.microecom.inventoryservice.model.data;

/**
 * Calculated available stock for a product.
 */
public interface CalculatedAvailable {
    String getForProductId();

    Integer getCalculated();
}
