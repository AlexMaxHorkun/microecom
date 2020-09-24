package com.microecom.customerservice.model.storage.customer.data;

import com.microecom.customerservice.model.data.ExistingCustomer;

import java.time.Instant;
import java.util.Optional;

public class Existing implements ExistingCustomer {
    private final String id;

    private final String userId;

    private final String firstName;

    private final String lastName;

    private final String email;

    private final Instant since;

    private final String defaultShippingAddressId;

    private final String defaultBillingAddressId;

    public Existing(String id, String userId, String firstName, String lastName, String email, Instant since, String shippingId, String billingId) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.since = since;
        this.defaultShippingAddressId = shippingId;
        this.defaultBillingAddressId = billingId;
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

    @Override
    public Optional<String> getDefaultShippingAddress() {
        if (defaultShippingAddressId == null) {
            return Optional.empty();
        }

        return Optional.of(defaultShippingAddressId);
    }

    @Override
    public Optional<String> getDefaultBillingAddress() {
        if (defaultBillingAddressId == null) {
            return Optional.empty();
        }

        return Optional.of(defaultBillingAddressId);
    }
}
