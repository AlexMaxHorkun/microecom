package com.microecom.catalogservice.event.consumer;

import com.microecom.catalogservice.model.event.InventoryListener;
import com.microecom.catalogservice.model.exception.ProductNotFoundException;
import com.microecom.inventoryservice.eventlist.StockChangedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Consumes Stock events.
 */
@Component
public class InventoryConsumer {
    private final InventoryListener inventoryListener;

    public InventoryConsumer(@Autowired InventoryListener inventoryListener) {
        this.inventoryListener = inventoryListener;
    }

    @KafkaListener(topics = {"stock-changed"})
    @Transactional
    public void listenToStockChanged(ConsumerRecord<String, StockChangedEvent> record) {
        try {
            inventoryListener.consumeStockChanged(record.value());
        } catch (ProductNotFoundException e) {
            //Nothing to do - product has been already deleted
        }
    }
}
