package com.microecom.orderservice.model.storage.order.data;

import com.microecom.orderservice.model.data.OrderedQuantity;

public class Ordered implements OrderedQuantity {
    private final String productId;

    private final Integer quantity;

    public Ordered(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    @Override
    public String getProductId() {
        return productId;
    }

    @Override
    public Integer getQuantity() {
        return quantity;
    }
}
