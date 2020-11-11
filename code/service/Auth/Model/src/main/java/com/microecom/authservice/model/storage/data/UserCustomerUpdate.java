package com.microecom.authservice.model.storage.data;

import java.util.UUID;

public final class UserCustomerUpdate {
    private final String userId;

    private final String customerEmail;

    private final String customerFirstname;

    private final String customerLastname;

    private final String customerId;

    public UserCustomerUpdate(String userId, String customerEmail, String customerFirstname, String customerLastname, String customerId) {
        this.userId = userId;
        this.customerEmail = customerEmail;
        this.customerFirstname = customerFirstname;
        this.customerLastname = customerLastname;
        this.customerId = customerId;
    }

    public UUID getUserId() {
        return UUID.fromString(userId);
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerFirstname() {
        return customerFirstname;
    }

    public String getCustomerLastname() {
        return customerLastname;
    }

    public String getCustomerId() {
        return customerId;
    }
}
