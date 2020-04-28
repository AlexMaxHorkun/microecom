package com.microecom.authservice.model.data;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Public user data.
 */
public class UserData implements User {
    private String id;

    private Instant timestamp;

    public UserData(String id, Instant timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    @Override
    public @NotNull String getId() {
        return id;
    }

    @Override
    public @NotNull ZonedDateTime getCreated() {
        return ZonedDateTime.ofInstant(timestamp, ZoneOffset.UTC);
    }
}
