package com.microecom.customerservice.model.storage.data;

import java.time.Instant;
import java.util.UUID;

public final class UserDataUpdate {
    private final String customerId;

    private final Instant created;

    public UserDataUpdate(String customerId, Instant created) {
        this.customerId = customerId;
        this.created = created;
    }

    public UUID getCustomerId() {
        return UUID.fromString(customerId);
    }

    public Instant getCreated() {
        return created;
    }
}
