package com.microecom.orderservice.model.data;

/**
 * Placed order.
 */
public interface ExistingOrder extends OrderInfo {
    String getId();

    OrderStatus getStatus();
}
