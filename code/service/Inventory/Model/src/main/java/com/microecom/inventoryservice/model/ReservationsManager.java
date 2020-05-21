package com.microecom.inventoryservice.model;

import com.microecom.inventoryservice.model.data.ActiveReservation;
import com.microecom.inventoryservice.model.data.NewReservation;
import com.microecom.inventoryservice.model.exception.InvalidReservationDataException;
import com.microecom.inventoryservice.model.exception.NoStockFoundException;
import com.microecom.inventoryservice.model.exception.ReservationNotFoundException;

import java.util.Optional;
import java.util.Set;

/**
 * Manages reservation.
 */
public interface ReservationsManager {
    Set<ActiveReservation> place(NewReservation reservation) throws NoStockFoundException, InvalidReservationDataException;

    Optional<Integer> calculateReservedFor(String productId);

    /**
     * Fulfills reservations made for an order.
     * @param orderId
     * @return List of products affected.
     * @throws ReservationNotFoundException When no reservations were found.
     */
    Set<String> fulfillByOrderId(String orderId) throws ReservationNotFoundException;

    /**
     * Cancels reservations made for the order.
     * @param orderId
     * @return List of affected products.
     * @throws ReservationNotFoundException When no reservations were found.
     */
    Set<String> cancelByOrderId(String orderId) throws ReservationNotFoundException;
}
