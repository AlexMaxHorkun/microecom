package com.microecom.authservice.model;

public interface PasswordEncryptor {
    String encrypt(String rawPassword);

    boolean isValid(String rawPassword, String encrypted);
}
