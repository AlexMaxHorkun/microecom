package com.microecom.inventoryservice.model.stock;

import com.microecom.inventoryservice.model.OrderEventConsumer;
import com.microecom.inventoryservice.model.ReservationsManager;
import com.microecom.inventoryservice.model.exception.NoStockFoundException;
import com.microecom.orderservice.eventlist.OrderStatusChanged;
import com.microecom.orderservice.eventlist.data.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderEventConsumerService implements OrderEventConsumer {
    private final ReservationsManager reservations;

    public OrderEventConsumerService(@Autowired ReservationsManager reservations) {
        this.reservations = reservations;
    }

    @Transactional
    @Override
    public void consumeOrderStatusChanged(OrderStatusChanged event) throws NoStockFoundException {
        if (event.getNewStatus() == OrderStatus.PROCESSED) {
            reservations.fulfillByOrderId(event.getOrderId());
        } else if (event.getNewStatus() == OrderStatus.CANCELED) {
            reservations.calculateReservedFor(event.getOrderId());
        }
    }
}
