package com.microecom.authservice.grpc.server.model.data;

import com.microecom.authservice.model.data.NewUserWithCredentials;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    public ZonedDateTime getCreated() {
        return created;
    }

    @Override
    @NotNull(message = "Password cannot be empty")
    @Size(min = 8, max = 255, message = "Password must be between {min} and {max} characters long")
    public String getPassword() {
        return password;
    }
}
