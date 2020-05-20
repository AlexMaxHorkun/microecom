package com.microecom.orderservice.model.client;

import com.microecom.orderservice.model.data.ProductInfo;

public class RestProductInfoResponse implements ProductInfo {
    private String id;

    private Double price;

    public RestProductInfoResponse(String id, Double price) {
        this.id = id;
        this.price = price;
    }

    public RestProductInfoResponse() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
