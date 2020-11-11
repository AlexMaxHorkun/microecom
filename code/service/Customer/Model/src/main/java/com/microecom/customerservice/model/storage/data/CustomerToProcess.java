package com.microecom.customerservice.model.storage.data;

public final class CustomerToProcess {
    private final String customerId;

    private final String email;

    private final String firstname;

    private final String lastname;

    private final String userId;

    public CustomerToProcess(String customerId, String email, String firstname, String lastname, String userId) {
        this.customerId = customerId;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.userId = userId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUserId() {
        return userId;
    }
}
