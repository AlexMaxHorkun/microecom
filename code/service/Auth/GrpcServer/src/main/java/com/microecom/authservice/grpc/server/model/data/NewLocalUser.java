package com.microecom.authservice.grpc.server.model.data;

import com.microecom.authservice.model.data.NewUserWithCredentials;

import java.time.ZonedDateTime;

public class NewLocalUser implements NewUserWithCredentials {
    private final String login;

    private final String password;

    private final ZonedDateTime created;

    public NewLocalUser(String login, String password, ZonedDateTime created) {
        this.login = login;
        this.password = password;
        this.created = created;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public ZonedDateTime getCreated() {
        return created;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
