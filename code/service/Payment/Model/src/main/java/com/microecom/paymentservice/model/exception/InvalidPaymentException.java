package com.microecom.paymentservice.model.exception;

public class InvalidPaymentException extends IllegalArgumentException {
    public InvalidPaymentException(String s) {
        super(s);
    }
}
