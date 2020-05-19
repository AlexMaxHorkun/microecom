package com.microecom.orderservice.model.client;

import com.microecom.orderservice.model.data.ProductStock;

public class RestStockResponse implements ProductStock {
    private String productId;

    private Integer available;

    public RestStockResponse(String productId, Integer available) {
        this.productId = productId;
        this.available = available;
    }

    public RestStockResponse() {}

    @Override
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}
