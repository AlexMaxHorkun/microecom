package com.microecom.orderservice.model.exception;

public class InvalidPaymentDetailsException extends IllegalArgumentException {
    public InvalidPaymentDetailsException() {
        super("Invalid payment details provided");
    }
}
