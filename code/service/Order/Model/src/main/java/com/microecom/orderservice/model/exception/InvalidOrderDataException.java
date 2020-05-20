package com.microecom.orderservice.model.exception;

public class InvalidOrderDataException extends IllegalArgumentException {
    public InvalidOrderDataException() {
        super("Invalid order data provided");
    }

    protected InvalidOrderDataException(String s) {
        super(s);
    }
}
