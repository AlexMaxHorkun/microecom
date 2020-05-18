package com.microecom.paymentservice.model;

import com.microecom.paymentservice.model.data.Event;

public interface EventPublisher {
    void publish(Event event);
}
