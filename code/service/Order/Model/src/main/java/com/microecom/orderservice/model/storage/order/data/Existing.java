package com.microecom.orderservice.model.storage.order.data;

import com.microecom.orderservice.model.data.ExistingOrder;
import com.microecom.orderservice.model.data.OrderStatus;
import com.microecom.orderservice.model.data.OrderedQuantity;

import java.util.Set;

public class Existing implements ExistingOrder {
    private final String id;

    private final OrderStatus status;

    private final String customerId;

    private final Set<OrderedQuantity> ordered;

    public Existing(String id, OrderStatus status, String customerId, Set<OrderedQuantity> ordered) {
        this.id = id;
        this.status = status;
        this.customerId = customerId;
        this.ordered = ordered;
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
    public Set<OrderedQuantity> getOrdered() {
        return ordered;
    }
}
