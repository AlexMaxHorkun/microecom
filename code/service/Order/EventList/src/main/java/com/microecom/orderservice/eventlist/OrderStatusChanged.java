package com.microecom.orderservice.eventlist;

import com.microecom.orderservice.eventlist.data.OrderStatus;
import com.microecom.orderservice.eventlist.data.OrderedProduct;

import java.util.Set;

/**
 * Thrown when order status changes.
 */
public class OrderStatusChanged {
    private String orderId;

    private Set<OrderedProduct> ordered;

    private OrderStatus newStatus;

    public OrderStatusChanged(String orderId, Set<OrderedProduct> ordered, OrderStatus newStatus) {
        this.orderId = orderId;
        this.ordered = ordered;
        this.newStatus = newStatus;
    }

    public OrderStatusChanged() {}

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(OrderStatus newStatus) {
        this.newStatus = newStatus;
    }

    public Set<OrderedProduct> getOrdered() {
        return ordered;
    }

    public void setOrdered(Set<OrderedProduct> ordered) {
        this.ordered = ordered;
    }
}
