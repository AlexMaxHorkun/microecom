package com.microecom.authservice.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class BCryptPasswordEncryptor implements PasswordEncryptor {
    private final BCryptPasswordEncoder encryptor;

    public BCryptPasswordEncryptor() {
        this.encryptor = new BCryptPasswordEncoder();
    }

    @Override
    public @NotNull String encrypt(@NotNull String rawPassword) {
        return encryptor.encode(rawPassword);
    }

    @Override
    public boolean isValid(String rawPassword, String encrypted) {
        return encryptor.matches(rawPassword, encrypted);
    }
}
