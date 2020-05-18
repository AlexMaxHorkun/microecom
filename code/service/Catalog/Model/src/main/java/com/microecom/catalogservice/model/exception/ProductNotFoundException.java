package com.microecom.catalogservice.model.exception;

public class ProductNotFoundException extends IllegalArgumentException {
    public ProductNotFoundException() {
        super("Product was not found by given criteria");
    }

    public ProductNotFoundException(Throwable cause) {
        super("Product was not found by given criteria", cause);
    }
}
