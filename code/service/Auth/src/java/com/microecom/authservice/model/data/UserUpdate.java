package com.microecom.authservice.model.data;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class UserUpdate implements UserWithCredentialsUpdate {
    private String userId;

    private final String password;

    public UserUpdate(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public void setUserId(@NotNull String id) {
        userId = id;
    }

    @Override
    public Optional<String> getNewPassword() {
        return password == null ? Optional.empty() : Optional.of(password);
    }

    @Override
    public String getUserId() {
        return userId;
    }
}
