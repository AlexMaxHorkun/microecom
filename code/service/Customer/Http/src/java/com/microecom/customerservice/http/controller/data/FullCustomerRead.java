package com.microecom.customerservice.http.controller.data;

import java.util.Optional;

/**
 * Full customer data available to themselves.
 */
public class FullCustomerRead extends CustomerRead {
    private final String login;

    private final String lastName;

    public FullCustomerRead(String email, String firstName, String id, String lastName) {
        super(email, firstName, id);
        login = null;
        this.lastName = lastName;
    }

    public FullCustomerRead(String email, String firstName, String id, String lastName, String login) {
        super(email, firstName, id);
        this.login = login;
        this.lastName = lastName;
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
}
