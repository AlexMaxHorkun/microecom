package com.microecom.customerservice.http.controller.data;

import com.microecom.customerservice.model.data.CustomerUpdate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Optional;

/**
 * Data provided by a customer to update their own account.
 */
public class SelfUpdate implements CustomerUpdate {
    private String id;

    private final String email;

    private final String firstName;

    private final String lastName;

    private final String shippingId;

    private final String billingId;

    public SelfUpdate(String email, String firstName, String lastName, String defaultShippingAddress, String defaultBillingAddress) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.shippingId = defaultShippingAddress;
        this.billingId = defaultBillingAddress;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getForId() {
        return id;
    }

    @Override
    public Optional<@Size(min = 5, max = 255) @Email String> getEmail() {
        return Optional.ofNullable(email);
    }

    @Override
    public Optional<@Size(min = 5, max = 255) String> getFirstName() {
        return Optional.ofNullable(firstName);
    }

    @Override
    public Optional<@Size(min = 5, max = 255) String> getLastName() {
        return Optional.ofNullable(lastName);
    }

    @Override
    public Optional<@Size(min = 5, max = 255) String> getDefaultShippingAddress() {
        return Optional.ofNullable(shippingId);
    }

    @Override
    public Optional<@Size(min = 5, max = 255) String> getDefaultBillingAddress() {
        return Optional.ofNullable(billingId);
    }
}
