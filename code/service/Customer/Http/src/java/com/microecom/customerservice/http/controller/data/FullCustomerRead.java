package com.microecom.customerservice.http.controller.data;

import java.util.Optional;

/**
 * Full customer data available to themselves.
 */
public class FullCustomerRead extends CustomerRead {
    private final String login;

    private final String lastName;

    private final String defaultShippingId;

    private final String defaultBillingId;

    public FullCustomerRead(String email, String firstName, String id, String lastName, String login, String shippingId, String billingId) {
        super(email, firstName, id);
        this.login = login;
        this.lastName = lastName;
        this.defaultShippingId = shippingId;
        this.defaultBillingId = billingId;
    }

    public Optional<String> getLogin() {
        if (login == null) {
            return Optional.empty();
        }

        return Optional.of(login);
    }

    public String getLastName() {
        return lastName;
    }

    public Optional<String> getDefaultShippingAddress() {
        return Optional.ofNullable(defaultShippingId);
    }

    public Optional<String> getDefaultBillingAddress() {
        return Optional.ofNullable(defaultBillingId);
    }
}
