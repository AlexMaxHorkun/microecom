package com.microecom.authservice.model.storage.data;

import java.time.Instant;

public class CustomerInfusedUser {
    private final String userId;

    private final String customerEmail;

    private final String customerFirstname;

    private final String customerLastname;

    private final String customerId;

    private final Instant created;

    public CustomerInfusedUser(String userId, String customerEmail, String customerFirstname, String customerLastname, String customerId, Instant created) {
        this.userId = userId;
        this.customerEmail = customerEmail;
        this.customerFirstname = customerFirstname;
        this.customerLastname = customerLastname;
        this.customerId = customerId;
        this.created = created;
    }

    public String getUserId() {
        return userId;
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

    public Instant getCreated() {
        return created;
    }
}
