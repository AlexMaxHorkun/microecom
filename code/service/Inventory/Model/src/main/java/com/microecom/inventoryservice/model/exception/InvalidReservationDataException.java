package com.microecom.inventoryservice.model.exception;

public class InvalidReservationDataException extends IllegalArgumentException {
    public InvalidReservationDataException() {
        super("Invalid reservation data provided");
    }

    public InvalidReservationDataException(Exception cause) {
        super("Invalid reservation data provided", cause);
    }
}
