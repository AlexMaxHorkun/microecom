package com.microecom.inventoryservice.model.stock;

import com.microecom.inventoryservice.model.OrderEventConsumer;
import com.microecom.inventoryservice.model.ReservationsManager;
import com.microecom.inventoryservice.model.data.reservation.ReservedProduct;
import com.microecom.inventoryservice.model.exception.NoStockFoundException;
import com.microecom.inventoryservice.model.reservation.data.NewOrderReservation;
import com.microecom.inventoryservice.model.reservation.data.NewOrdered;
import com.microecom.orderservice.eventlist.OrderStatusChanged;
import com.microecom.orderservice.eventlist.data.OrderStatus;
import com.microecom.orderservice.eventlist.data.OrderedProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
public class OrderEventConsumerService implements OrderEventConsumer {
    private final ReservationsManager reservations;

    public OrderEventConsumerService(@Autowired ReservationsManager reservations) {
        this.reservations = reservations;
    }

    @Transactional
    @Override
    public void consumeOrderStatusChanged(OrderStatusChanged event) throws NoStockFoundException {
        if (event.getNewStatus() == OrderStatus.NEW) {
            var reserved = new HashSet<ReservedProduct>();
            for (OrderedProduct p : event.getOrdered()) {
                reserved.add(new NewOrdered(p.getProductId(), p.getQuantity()));
            }

            reservations.place(new NewOrderReservation(event.getOrderId(), reserved));
        } else if (event.getNewStatus() == OrderStatus.PROCESSED) {
            reservations.fulfillByOrderId(event.getOrderId());
        } else if (event.getNewStatus() == OrderStatus.CANCELED) {
            reservations.calculateReservedFor(event.getOrderId());
        }
    }
}
