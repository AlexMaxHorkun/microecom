package com.microecom.catalogservice.event.consumer;

import com.microecom.catalogservice.model.ProductManager;
import com.microecom.inventoryservice.eventlist.StockChangedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumes Stock events.
 */
@Component
public class StockServiceEventConsumer {
    private final ProductManager products;

    public StockServiceEventConsumer(@Autowired ProductManager products) {
        this.products = products;
    }

    @KafkaListener(topics = {"stock-changed"})
    public void listenToChange(ConsumerRecord<String, StockChangedEvent> record) {
        try {
            products.consumeStockUpdate(record.value());
        } catch (IllegalArgumentException e) {
            //Nothing to do
        }
    }
}
