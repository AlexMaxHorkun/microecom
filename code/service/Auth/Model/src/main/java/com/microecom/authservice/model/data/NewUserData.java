package com.microecom.authservice.model.data;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * New User authenticated by login and password to be created.
 */
public class NewUserData implements NewUserWithCredentials {
    private final ZonedDateTime created;

    private final String login;

    private final String password;

    public NewUserData(String login, String password) {
        this.created = ZonedDateTime.now(ZoneOffset.UTC);
        this.login = login;
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public ZonedDateTime getCreated() {
        return created;
    }
}
