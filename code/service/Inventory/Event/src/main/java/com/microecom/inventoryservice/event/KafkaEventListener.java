package com.microecom.inventoryservice.event;

import com.microecom.inventoryservice.model.OrderEventConsumer;
import com.microecom.orderservice.eventlist.OrderStatusChanged;
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
    private final OrderEventConsumer orders;

    public KafkaEventListener(@Autowired OrderEventConsumer orders) {
        this.orders = orders;
    }

    @KafkaListener(topics = {"order-status-changed"})
    @Transactional
    public void listenToOrderStatusUpdate(ConsumerRecord<String, OrderStatusChanged> record) {
        orders.consumeOrderStatusChanged(record.value());
    }
}
