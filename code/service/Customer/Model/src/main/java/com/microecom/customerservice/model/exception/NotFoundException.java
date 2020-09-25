package com.microecom.customerservice.model.exception;

import java.util.Optional;

public class NotFoundException extends IllegalArgumentException {
    private final String id;

    public NotFoundException(String message) {
        super(message);
        id = null;
    }

    public NotFoundException(String message, String id) {
        super(message);
        this.id = id;
    }

    public Optional<String> getId() {
        return Optional.ofNullable(id);
    }
}
