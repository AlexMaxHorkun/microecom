package com.microecom.customerservice.model.client.exception;

import com.microecom.customerservice.model.exception.InvalidInputDataException;

import java.util.Map;
import java.util.Set;

/**
 * Thrown when new user data provided was invalid.
 */
public class InvalidUserDataException extends InvalidInputDataException {
    public InvalidUserDataException(String message) {
        super(message);
    }

    public InvalidUserDataException(Map<String, Set<String>> fieldViolations) {
        super("Invalid user data provided", "user", fieldViolations);
    }
}
