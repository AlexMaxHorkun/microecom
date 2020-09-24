package com.microecom.customerservice.model.client.data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewUser {
    private final String login;

    private final String password;

    public NewUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public @NotNull @NotBlank String getLogin() {
        return login;
    }

    public @NotNull @NotBlank String getPassword() {
        return password;
    }
}
