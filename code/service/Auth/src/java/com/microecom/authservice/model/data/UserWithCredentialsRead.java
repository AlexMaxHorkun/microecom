package com.microecom.authservice.model.data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * User with credentials info.
 */
public class UserWithCredentialsRead extends UserData implements User, UserWithLogin {
    private final String login;

    public UserWithCredentialsRead(String id, Instant timestamp, String login) {
        super(id, timestamp);
        this.login = login;
    }

    @Override
    public @NotNull String getLogin() {
        return login;
    }
}
