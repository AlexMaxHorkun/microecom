package com.microecom.customerservice.model.exception;

import java.util.Map;
import java.util.Set;

/**
 * Thrown when invalid address data has been provided.
 */
public class InvalidAddressDataException extends InvalidInputDataException {
    public InvalidAddressDataException(String message) {
        super(message);
    }

    public InvalidAddressDataException(Map<String, Set<String>> fieldViolations) {
        super(fieldViolations);
    }
}
