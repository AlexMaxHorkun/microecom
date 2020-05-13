package com.microecom.inventoryservice.eventlist;

/**
 * Issued when stock for a product is updated.
 */
public class StockChangedEvent {
    private String productId;

    private Integer available;

    public StockChangedEvent() {}

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

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}
