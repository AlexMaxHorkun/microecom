package com.microecom.catalogservice.model.storage.data;

/**
 * Update for a product's available field.
 */
public class ProductAvailabilityUpdate {
    private final String productId;

    private final Boolean available;

    public ProductAvailabilityUpdate(String productId, Boolean available) {
        this.productId = productId;
        this.available = available;
    }

    public String getForProductId() {
        return productId;
    }

    public Boolean isAvailable() {
        return available;
    }
}
