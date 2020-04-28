package com.microecom.authservice.model;

import javax.validation.constraints.NotNull;

public interface PasswordEncryptor {
    public @NotNull String encrypt(@NotNull String rawPassword);

    public boolean isValid(@NotNull String rawPassword, @NotNull String encrypted);
}
