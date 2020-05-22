package com.microecom.orderservice.model.event;

import com.microecom.orderservice.model.OrderManager;
import com.microecom.orderservice.model.PaymentEventsConsumer;
import com.microecom.orderservice.model.data.OrderStatus;
import com.microecom.orderservice.model.data.OrderStatusUpdate;
import com.microecom.orderservice.model.exception.InvalidOrderStatusException;
import com.microecom.orderservice.model.exception.OrderNotFoundException;
import com.microecom.paymentservice.eventlist.PaymentProcessedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer implements PaymentEventsConsumer {
    private final OrderManager manager;

    public PaymentConsumer(@Autowired OrderManager manager) {
        this.manager = manager;
    }

    @Override
    public void consumePaymentProcessed(PaymentProcessedEvent event) throws InvalidOrderStatusException, OrderNotFoundException {
        var status = event.getSuccessful() ? OrderStatus.PROCESSED : OrderStatus.PAYMENT_FAILED;
        try {
            manager.update(new OrderStatusUpdate(event.getOrderId(), status));
        } catch (InvalidOrderStatusException e) {
            //Already canceled
        }
    }
}
