package com.microecom.customerservice.http.controller.data;

import com.microecom.customerservice.model.data.SigningUp;

import javax.validation.constraints.*;
import java.util.Optional;

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
    @Size(min = 5, max = 255, message = "Login must be between {min} and {max} characters long")
    @NotNull(message = "Login cannot be empty")
    @Pattern(
            regexp = "^[A-z0-9_\\-]+$",
            message = "Login can only contain alphanumerical characters plus \"-\" and \"_\""
    )
    public String getLogin() {
        return login;
    }

    @Override
    @NotNull(message = "Password cannot be empty")
    @Size(min = 8, max = 255, message = "Password must be between {min} and {max} characters long")
    public String getPassword() {
        return password;
    }

    @Override
    @NotNull(message = "First name cannot be empty")
    @Pattern(regexp = "^[A-z\\.]{2,125}$", message = "First name must be between 2 and 125 alpha characters")
    public String getFirstName() {
        return firstName;
    }

    @Override
    @NotNull(message = "Last name cannot be empty")
    @Pattern(regexp = "^[A-z\\.]{2,125}$", message = "Last name must be between 2 and 125 alpha characters")
    public String getLastName() {
        return lastName;
    }

    @Override
    @NotNull(message = "E-mail cannot be empty")
    @Size(min = 5, max = 255, message = "E-mail must be between {min} and {max} characters")
    @Email(message = "Invalid E-mail provided")
    public String getEmail() {
        return email;
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
