package com.microecom.orderservice.event;

import com.microecom.orderservice.model.PaymentEventsConsumer;
import com.microecom.orderservice.model.exception.InvalidOrderStatusException;
import com.microecom.orderservice.model.exception.OrderNotFoundException;
import com.microecom.paymentservice.eventlist.PaymentProcessedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Listens to events coming from Kafka.
 */
@Component
public class KafkaEventListener {
    private final PaymentEventsConsumer payment;

    public KafkaEventListener(@Autowired PaymentEventsConsumer payment) {
        this.payment = payment;
    }

    @KafkaListener(topics = {"payment-processed"})
    @Transactional
    public void listenToStockChanged(ConsumerRecord<String, PaymentProcessedEvent> record) {
        try {
            payment.consumePaymentProcessed(record.value());
        } catch (OrderNotFoundException e) {
            //Nothing to do - order was already deleted
        } catch (InvalidOrderStatusException e) {
            //Nothing to do - order status has been changed from NEW/PAYMENT_FAILED
        }
    }
}
