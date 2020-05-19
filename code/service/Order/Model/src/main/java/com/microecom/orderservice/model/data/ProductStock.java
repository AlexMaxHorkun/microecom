package com.microecom.orderservice.model.data;

/**
 * Stock found for a product.
 */
public interface ProductStock {
    String getProductId();

    Integer getAvailable();
}
