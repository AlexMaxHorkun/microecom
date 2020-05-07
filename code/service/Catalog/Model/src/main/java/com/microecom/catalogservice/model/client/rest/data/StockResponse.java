package com.microecom.catalogservice.model.client.rest.data;

import com.microecom.catalogservice.model.client.data.Stock;

public class StockResponse implements Stock {
    private String productId;

    private Integer available;

    public StockResponse(String productId, Integer available) {
        this.productId = productId;
        this.available = available;
    }

    public StockResponse() {}

    @Override
    public String getProductId() {
        return productId;
    }

    @Override
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
