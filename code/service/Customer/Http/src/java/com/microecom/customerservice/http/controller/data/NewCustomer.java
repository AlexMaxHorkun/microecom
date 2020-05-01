package com.microecom.customerservice.http.controller.data;

import com.microecom.customerservice.model.data.SigningUp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Customer registering via customers service.
 */
public class NewCustomer implements SigningUp {
    private final String email;

    private final String login;

    private final String firstName;

    private final String lastName;

    private final String password;

    public NewCustomer(String email, String login, String firstName, String lastName, String password) {
        this.email = email;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    @Override
    public @NotNull @NotBlank @Size(min = 5, max = 255) String getLogin() {
        return login;
    }

    @Override
    public @NotNull @NotBlank @Size(min = 5, max = 255) String getPassword() {
        return password;
    }

    @Override
    public @NotNull @NotBlank @Size(min = 5, max = 255) String getFirstName() {
        return firstName;
    }

    @Override
    public @NotNull @NotBlank @Size(min = 5, max = 255) String getLastName() {
        return lastName;
    }

    @Override
    public @NotNull @NotBlank @Size(min = 5, max = 255) @Email String getEmail() {
        return email;
    }
}
