package com.microecom.orderservice.eventlist;

import com.microecom.orderservice.eventlist.data.OrderStatus;

import java.util.Set;

/**
 * Thrown when order status changes.
 */
public class OrderStatusChanged {
    private String orderId;

    private Set<String> productIds;

    private OrderStatus newStatus;

    public OrderStatusChanged(String orderId, Set<String> productIds, OrderStatus newStatus) {
        this.orderId = orderId;
        this.productIds = productIds;
        this.newStatus = newStatus;
    }

    public OrderStatusChanged() {}

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Set<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<String> productIds) {
        this.productIds = productIds;
    }

    public OrderStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(OrderStatus newStatus) {
        this.newStatus = newStatus;
    }
}
