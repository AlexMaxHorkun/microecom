package com.microecom.orderservice.model.storage.data;

import com.microecom.orderservice.model.data.OrderStatus;

import java.util.Set;

public class SimpleOrder implements Order {
    private final OrderStatus status;

    private final String customerId;

    private final Set<String> productIds;

    public SimpleOrder(OrderStatus status, String customerId, Set<String> productIds) {
        this.status = status;
        this.customerId = customerId;
        this.productIds = productIds;
    }

    @Override
    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public String getCustomerId() {
        return customerId;
    }

    @Override
    public Set<String> getProductIds() {
        return productIds;
    }
}
