package com.microecom.authservice.model.storage.exception;

public class NotUniqueException extends RuntimeException {
    public NotUniqueException() {
        super("New record data is not unique");
    }

    public NotUniqueException(Throwable cause) {
        super("New record data is not unique", cause);
    }

    public NotUniqueException(String message) {
        super(message);
    }
}
