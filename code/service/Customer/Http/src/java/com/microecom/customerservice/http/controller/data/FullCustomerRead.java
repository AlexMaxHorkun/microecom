package com.microecom.customerservice.http.controller.data;

/**
 * Full customer data available to themselves.
 */
public class FullCustomerRead extends CustomerRead {
    private final String login;

    private final String lastName;

    public FullCustomerRead(String email, String firstName, String id, String login, String lastName) {
        super(email, firstName, id);
        this.login = login;
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getLastName() {
        return lastName;
    }
}
