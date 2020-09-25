package com.microecom.customerservice.model.exception;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class InvalidInputDataException extends IllegalArgumentException {
    private final Map<String, Set<String>> fieldViolations;

    public InvalidInputDataException(String message) {
        super(message);
        fieldViolations = null;
    }

    public InvalidInputDataException(Map<String, Set<String>> fieldViolations) {
        super("Invalid user data provided");
        this.fieldViolations = fieldViolations;
    }

    public Optional<Map<String, Set<String>>> getFieldViolations() {
        return Optional.ofNullable(fieldViolations);
    }
}
