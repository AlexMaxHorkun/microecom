package com.microecom.orderservice.model.storage.data;

import com.microecom.orderservice.model.data.OrderStatus;
import com.microecom.orderservice.model.data.OrderedQuantity;

import java.util.Set;

public class SimpleOrder implements Order {
    private final OrderStatus status;

    private final String customerId;

    private final Set<OrderedQuantity> ordered;

    private final Double cost;

    public SimpleOrder(OrderStatus status, String customerId, Set<OrderedQuantity> ordered, Double cost) {
        this.status = status;
        this.customerId = customerId;
        this.ordered = ordered;
        this.cost = cost;
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

    @Override
    public Double getCost() {
        return cost;
    }
}
