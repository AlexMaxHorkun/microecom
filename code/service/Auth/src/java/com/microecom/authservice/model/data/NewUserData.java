package com.microecom.authservice.model.data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * New User to be created.
 */
public class NewUserData implements NewUserWithCredentials {
    private final ZonedDateTime created;

    private final String login;

    private final String password;

    public NewUserData(
            @NotNull @Size(min=5, max=255) String login,
            @NotNull @Size(min=5, max=255) String password
    ) {
        this.created = ZonedDateTime.now(ZoneOffset.UTC);
        this.login = login;
        this.password = password;
    }

    @Override
    public @NotNull String getPassword() {
        return password;
    }

    @Override
    public @NotNull String getLogin() {
        return login;
    }

    @Override
    public @NotNull ZonedDateTime getCreated() {
        return created;
    }
}
