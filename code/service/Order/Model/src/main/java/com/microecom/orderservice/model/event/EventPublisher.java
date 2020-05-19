package com.microecom.orderservice.model.event;

import com.microecom.orderservice.model.event.data.Event;

public interface EventPublisher {
    void publish(Event event);
}
