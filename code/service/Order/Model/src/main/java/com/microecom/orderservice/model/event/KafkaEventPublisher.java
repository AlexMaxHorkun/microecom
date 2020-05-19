package com.microecom.orderservice.model.event;

import com.microecom.orderservice.model.event.data.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KafkaEventPublisher implements EventPublisher {
    private final KafkaTemplate<String, Object> template;

    public KafkaEventPublisher(@Autowired KafkaTemplate<String, Object> kafkaTemplate) {
        this.template = kafkaTemplate;
    }

    @Transactional
    @Override
    public void publish(Event event) {
        template.send(event.getId(), event.getMessage());
    }
}
