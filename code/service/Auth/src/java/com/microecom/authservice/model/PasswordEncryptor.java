package com.microecom.authservice.model;

import javax.validation.constraints.NotNull;

public interface PasswordEncryptor {
    @NotNull String encrypt(@NotNull String rawPassword);

    boolean isValid(@NotNull String rawPassword, @NotNull String encrypted);
}
