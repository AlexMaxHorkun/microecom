package com.microecom.inventoryservice.model.data;

/**
 * Stock data for a product.
 */
public interface Stock {
    String getProductId();

    int getAvailable();
}
