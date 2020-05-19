package com.microecom.paymentservice.model;

import com.microecom.paymentservice.model.data.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventPublisher implements EventPublisher {
    private final KafkaTemplate<String, Object> template;

    public KafkaEventPublisher(@Autowired KafkaTemplate<String, Object> kafkaTemplate) {
        this.template = kafkaTemplate;
    }

    @Override
    public void publish(Event event) {
        template.send(event.getEventId(), event.getData());
    }
}
