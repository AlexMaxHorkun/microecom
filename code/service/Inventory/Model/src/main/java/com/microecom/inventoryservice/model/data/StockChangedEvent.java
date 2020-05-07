package com.microecom.inventoryservice.model.data;

/**
 * Issued when stock for a product is updated.
 */
public class StockChangedEvent {
    private final String productId;

    private final Integer available;

    public StockChangedEvent(String productId, Integer available) {
        this.productId = productId;
        this.available = available;
    }

    public String getProductId() {
        return productId;
    }

    public Integer getAvailable() {
        return available;
    }
}
