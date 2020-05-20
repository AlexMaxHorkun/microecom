package com.microecom.orderservice.model.exception;

public class InvalidOrderStatusException extends InvalidOrderDataException {
    public InvalidOrderStatusException() {
        super("Invalid status provided");
    }
}
