package com.microecom.orderservice.model.storage.order.data;

import com.microecom.orderservice.model.data.ExistingOrder;
import com.microecom.orderservice.model.data.OrderStatus;

import java.util.Set;

public class Existing implements ExistingOrder {
    private final String id;

    private final OrderStatus status;

    private final String customerId;

    private final Set<String> productIds;

    public Existing(String id, OrderStatus status, String customerId, Set<String> productIds) {
        this.id = id;
        this.status = status;
        this.customerId = customerId;
        this.productIds = productIds;
    }

    @Override
    public String getId() {
        return id;
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
