package com.microecom.customerservice.model.exception;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class InvalidInputDataException extends IllegalArgumentException {
    private final Map<String, Set<String>> fieldViolations;

    private final String objectName;

    public InvalidInputDataException(String message) {
        super(message);
        fieldViolations = null;
        objectName = null;
    }

    public InvalidInputDataException(String message, String objectName, Map<String, Set<String>> fieldViolations) {
        super(message);
        this.fieldViolations = fieldViolations;
        this.objectName = objectName;
    }

    public Optional<Map<String, Set<String>>> getFieldViolations() {
        return Optional.ofNullable(fieldViolations);
    }

    public Optional<String> getObjectName() {
        return Optional.ofNullable(objectName);
    }
}
