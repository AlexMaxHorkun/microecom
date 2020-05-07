package com.microecom.inventoryservice.model.storage.stock.data;

import com.microecom.inventoryservice.model.data.Stock;

public class ExistingStock implements Stock {
    private String productId;

    private Integer available;

    public ExistingStock() {}

    public ExistingStock(String productId, Integer available) {
        this.productId = productId;
        this.available = available;
    }

    @Override
    public String getProductId() {
        return productId;
    }

    @Override
    public int getAvailable() {
        return available;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}
