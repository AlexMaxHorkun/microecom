package com.microecom.inventoryservice.model.storage.reservation.data;

import java.util.Optional;

public class ReservationUpdate implements com.microecom.inventoryservice.model.storage.data.ReservationUpdate {
    private final String forId;

    private final Boolean fulfilled;

    public ReservationUpdate(String forId, Boolean fulfilled) {
        this.forId = forId;
        this.fulfilled = fulfilled;
    }

    @Override
    public String getForReservationId() {
        return forId;
    }

    @Override
    public Optional<Boolean> getFulfilled() {
        return Optional.ofNullable(fulfilled);
    }
}
