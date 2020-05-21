package com.microecom.inventoryservice.model.storage.data;

import java.util.Optional;

/**
 * Update for a reservation.
 */
public interface ReservationUpdate {
    String getForReservationId();

    Optional<Boolean> getFulfilled();
}
