package com.microecom.inventoryservice.model.kafka;

import com.microecom.inventoryservice.model.EventPublisher;
import com.microecom.inventoryservice.model.data.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender implements EventPublisher {
    private final KafkaTemplate<String, Object> template;

    public Sender(@Autowired KafkaTemplate<String, Object> kafkaTemplate) {
        this.template = kafkaTemplate;
    }

    @Override
    public void publish(Event event) {
        template.send(event.getId(), event.getMessage());
    }
}
