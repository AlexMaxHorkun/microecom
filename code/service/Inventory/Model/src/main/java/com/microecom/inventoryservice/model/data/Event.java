package com.microecom.inventoryservice.model.data;

/**
 * Event to be published.
 */
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
