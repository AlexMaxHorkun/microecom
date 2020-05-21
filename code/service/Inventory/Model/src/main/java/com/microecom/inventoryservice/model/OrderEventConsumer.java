package com.microecom.inventoryservice.model;

import com.microecom.inventoryservice.model.exception.NoStockFoundException;
import com.microecom.orderservice.eventlist.OrderStatusChanged;

/**
 * Consumers events from Order service.
 */
public interface OrderEventConsumer {
    void consumeOrderStatusChanged(OrderStatusChanged event) throws NoStockFoundException;
}
