package com.microecom.orderservice.model.exception;

public class OrderNotFoundException extends IllegalArgumentException {
    public OrderNotFoundException() {
        super("Order was not found");
    }
}
