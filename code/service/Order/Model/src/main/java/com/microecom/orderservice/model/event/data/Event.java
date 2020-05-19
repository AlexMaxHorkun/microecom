package com.microecom.orderservice.model.event.data;

public class Event {
    private final String id;

    private final Object message;

    public Event(String id, Object message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public Object getMessage() {
        return message;
    }
}
