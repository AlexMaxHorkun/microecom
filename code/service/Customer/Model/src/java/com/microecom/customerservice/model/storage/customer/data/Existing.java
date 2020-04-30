package com.microecom.customerservice.model.storage.customer.data;

import com.microecom.customerservice.model.data.ExistingCustomer;

import java.time.Instant;

public class Existing implements ExistingCustomer {
    private final String id;

    private final String userId;

    private final String firstName;

    private final String lastName;

    private final String email;

    private final Instant since;

    public Existing(String id, String userId, String firstName, String lastName, String email, Instant since) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.since = since;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Instant getSince() {
        return since;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
