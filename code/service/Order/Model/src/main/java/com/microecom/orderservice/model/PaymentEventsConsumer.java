package com.microecom.orderservice.model;

import com.microecom.orderservice.model.exception.InvalidOrderStatusException;
import com.microecom.orderservice.model.exception.OrderNotFoundException;
import com.microecom.paymentservice.eventlist.PaymentProcessedEvent;

/**
 * Consumes events from Payment service.
 */
public interface PaymentEventsConsumer {
    void consumePaymentProcessed(PaymentProcessedEvent event) throws InvalidOrderStatusException, OrderNotFoundException;
}
