package com.microecom.authservice.model.data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class UserWithCredentialsData extends UserWithCredentialsRead implements UserWithCredentials {
    private String password;

    public UserWithCredentialsData(
            @NotNull String id,
            @NotNull Instant timestamp,
            @NotNull String login,
            @NotNull String password
    ) {
        super(id, timestamp, login);
        this.password = password;
    }

    @Override
    public @NotNull String getPasswordHash() {
        return password;
    }
}
