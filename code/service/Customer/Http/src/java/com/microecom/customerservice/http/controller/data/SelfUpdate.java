package com.microecom.customerservice.http.controller.data;

import com.microecom.customerservice.model.data.CustomerUpdate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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

    public SelfUpdate(String email, String firstName, String lastName, String shippingId, String billingId) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.shippingId = shippingId;
        this.billingId = billingId;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getForId() {
        return id;
    }

    @Override
    public @NotBlank @Size(min = 5, max = 255) @Email Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    @Override
    public @NotBlank @Size(min = 5, max = 255) Optional<String> getFirstName() {
        return Optional.ofNullable(firstName);
    }

    @Override
    public @NotBlank @Size(min = 5, max = 255) Optional<String> getLastName() {
        return Optional.ofNullable(lastName);
    }

    @Override
    public @NotBlank Optional<String> getDefaultShippingAddress() {
        return Optional.ofNullable(shippingId);
    }

    @Override
    public @NotBlank Optional<String> getDefaultBillingAddress() {
        return Optional.ofNullable(billingId);
    }
}
