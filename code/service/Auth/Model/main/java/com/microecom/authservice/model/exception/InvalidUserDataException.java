package com.microecom.authservice.model.exception;

public class InvalidUserDataException extends IllegalArgumentException {
    public InvalidUserDataException(String userMessage) {
        super(userMessage);
    }
}
