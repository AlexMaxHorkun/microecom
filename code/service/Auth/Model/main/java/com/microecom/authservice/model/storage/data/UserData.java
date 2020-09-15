package com.microecom.authservice.model.storage.data;

import com.microecom.authservice.model.data.User;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Public user data.
 */
public class UserData implements User {
    private final String id;

    private final Instant timestamp;

    public UserData(String id, Instant timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ZonedDateTime getCreated() {
        return ZonedDateTime.ofInstant(timestamp, ZoneOffset.UTC);
    }
}
