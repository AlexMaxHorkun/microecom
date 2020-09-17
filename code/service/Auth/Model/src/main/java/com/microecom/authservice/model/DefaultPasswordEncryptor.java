package com.microecom.authservice.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultPasswordEncryptor implements PasswordEncryptor, PasswordEncoder {
    private final PasswordEncoder encryptor;

    public DefaultPasswordEncryptor() {
        this.encryptor = new BCryptPasswordEncoder();
    }

    @Override
    public String encrypt(String rawPassword) {
        return encode(rawPassword);
    }

    @Override
    public boolean isValid(String rawPassword, String encrypted) {
        return matches(rawPassword, encrypted);
    }

    @Override
    public String encode(CharSequence charSequence) {
        return encryptor.encode(charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return encryptor.matches(charSequence, s);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return encryptor.upgradeEncoding(encodedPassword);
    }
}
