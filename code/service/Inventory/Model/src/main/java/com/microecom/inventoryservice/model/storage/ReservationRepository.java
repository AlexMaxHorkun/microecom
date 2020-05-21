package com.microecom.inventoryservice.model.storage;

import com.microecom.inventoryservice.model.data.ActiveReservation;
import com.microecom.inventoryservice.model.data.NewReservation;
import com.microecom.inventoryservice.model.exception.ReservationNotFoundException;
import com.microecom.inventoryservice.model.storage.data.ReservationUpdate;

import java.util.Set;

public interface ReservationRepository {
    Set<ActiveReservation> create(NewReservation reservation);

    Integer calculateUnfulfilledForProductId(String id);

    ActiveReservation update(ReservationUpdate update) throws ReservationNotFoundException;

    void delete(String id) throws ReservationNotFoundException;

    Set<ActiveReservation> findUnfulfilledByOrderId(String id);
}
