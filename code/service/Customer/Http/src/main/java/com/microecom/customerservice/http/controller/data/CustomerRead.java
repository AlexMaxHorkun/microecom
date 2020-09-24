package com.microecom.customerservice.http.controller.data;

/**
 * Public customer data.
 */
public class CustomerRead {
    private final String email;

    private final String firstName;

    private final String id;

    public CustomerRead(String email, String firstName, String id) {
        this.email = email;
        this.firstName = firstName;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }
}
