package com.microecom.paymentservice.model.data;

public class Event {
    private final String eventId;

    private final Object data;

    public Event(String eventId, Object data) {
        this.eventId = eventId;
        this.data = data;
    }

    public String getEventId() {
        return eventId;
    }

    public Object getData() {
        return data;
    }
}
