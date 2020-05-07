package com.microecom.inventoryservice.http.controller.data;

import com.microecom.inventoryservice.model.data.Stock;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Stock input
 */
public class NewStock implements Stock {
    private String productId;

    private Integer available;

    public NewStock(String productId, Integer available) {
        this.productId = productId;
        this.available = available;
    }

    public NewStock() {}

    @Override
    public @NotBlank String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public @Min(0) int getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}
