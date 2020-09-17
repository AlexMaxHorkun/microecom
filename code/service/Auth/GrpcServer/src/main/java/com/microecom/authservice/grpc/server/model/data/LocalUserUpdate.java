package com.microecom.authservice.grpc.server.model.data;

import com.microecom.authservice.model.data.UserWithCredentialsUpdate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

public class LocalUserUpdate implements UserWithCredentialsUpdate {
    private final String id;

    private final String password;

    public LocalUserUpdate(String id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public Optional<@Size(min = 8, max = 255, message = "Password must be between {min} and {max} characters long") String> getNewPassword() {
        return Optional.ofNullable(password);
    }

    @Override
    @NotNull(message = "User ID is required")
    public String getUserId() {
        return id;
    }
}
