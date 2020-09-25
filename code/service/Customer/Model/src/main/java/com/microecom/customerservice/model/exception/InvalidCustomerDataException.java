package com.microecom.customerservice.model.exception;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Thrown when new customer data provided was invalid.
 */
public class InvalidCustomerDataException extends InvalidInputDataException {
    public InvalidCustomerDataException(String message) {
        super(message);
    }

    public InvalidCustomerDataException(Map<String, Set<String>> fieldViolations) {
        super(fieldViolations);
    }
}
