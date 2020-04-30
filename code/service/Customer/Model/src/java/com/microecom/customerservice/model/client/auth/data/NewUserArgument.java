package com.microecom.customerservice.model.client.auth.data;

public class NewUserArgument {
    private final String login;

    private final String password;

    public NewUserArgument(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
