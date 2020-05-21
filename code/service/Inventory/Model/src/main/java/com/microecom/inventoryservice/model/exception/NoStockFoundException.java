package com.microecom.inventoryservice.model.exception;

/**
 * Thrown when stock info was not found.
 */
public class NoStockFoundException extends IllegalArgumentException {
    public NoStockFoundException(String productId) {
        super("Stock for product " + productId + " was not found");
    }

    public NoStockFoundException() {
        super("some stocks were not found");
    }
}
