package com.microecom.inventoryservice.model;

import com.microecom.inventoryservice.model.data.Event;

/**
 * Publishes events.
 */
public interface EventPublisher {
    void publish(Event event);
}
