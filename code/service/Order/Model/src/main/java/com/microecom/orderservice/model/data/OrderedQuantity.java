package com.microecom.orderservice.model.data;

/**
 * Ordered product.
 */
public interface OrderedQuantity {
    String getProductId();

    Integer getQuantity();
}
