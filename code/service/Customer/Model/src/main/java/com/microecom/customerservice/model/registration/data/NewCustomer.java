package com.microecom.customerservice.model.registration.data;

import com.microecom.customerservice.model.data.Customer;

import java.util.Optional;

public class NewCustomer implements Customer {
    private final String firstName;

    private final String lastName;

    private final String email;

    private final String userId;

    public NewCustomer(String firstName, String lastName, String email, String userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userId = userId;
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
    public String getUserId() {
        return userId;
    }

    @Override
    public Optional<String> getDefaultShippingAddress() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getDefaultBillingAddress() {
        return Optional.empty();
    }
}
