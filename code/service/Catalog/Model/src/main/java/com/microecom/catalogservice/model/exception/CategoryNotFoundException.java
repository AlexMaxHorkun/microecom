package com.microecom.catalogservice.model.exception;

public class CategoryNotFoundException extends IllegalArgumentException {
    public CategoryNotFoundException() {
        super("Category was not found by given criteria");
    }

    public CategoryNotFoundException(Throwable cause) {
        super("Category was not found by given criteria", cause);
    }
}
