package com.microecom.orderservice.model.exception;

import java.util.Set;

public class OutOfStockException extends IllegalArgumentException {
    public OutOfStockException(Set<String> productIds) {
        super("Products are out of stock: " + String.join(", ", productIds));
    }
}
