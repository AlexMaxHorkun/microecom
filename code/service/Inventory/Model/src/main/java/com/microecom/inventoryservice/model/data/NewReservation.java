package com.microecom.inventoryservice.model.data;

import com.microecom.inventoryservice.model.data.reservation.ReservedProduct;

import java.util.Set;

/**
 * Reservation made for an order.
 */
public interface NewReservation {
    String getOrderId();

    Set<ReservedProduct> getReserved();
}
