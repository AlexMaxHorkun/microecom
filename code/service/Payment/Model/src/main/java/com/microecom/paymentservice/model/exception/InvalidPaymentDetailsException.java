package com.microecom.paymentservice.model.exception;

/**
 * Thrown when invalid payment was rejected immediately.
 */
public class InvalidPaymentDetailsException extends IllegalArgumentException {
    public InvalidPaymentDetailsException(String s) {
        super(s);
    }
}
