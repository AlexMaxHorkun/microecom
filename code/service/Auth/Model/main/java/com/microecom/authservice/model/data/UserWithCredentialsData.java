package com.microecom.authservice.model.data;

import java.time.Instant;

public class UserWithCredentialsData extends UserWithCredentialsRead implements UserWithCredentials {
    private final String password;

    public UserWithCredentialsData(String id, Instant timestamp, String login, String password) {
        super(id, timestamp, login);
        this.password = password;
    }

    @Override
    public String getPasswordHash() {
        return password;
    }
}
