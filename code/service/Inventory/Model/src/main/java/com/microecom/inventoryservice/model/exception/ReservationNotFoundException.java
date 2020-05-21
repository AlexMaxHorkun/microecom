package com.microecom.inventoryservice.model.exception;

public class ReservationNotFoundException extends IllegalArgumentException {
    public ReservationNotFoundException() {
        super("Reservation not found");
    }
}
