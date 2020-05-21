package com.microecom.inventoryservice.model.reservation.data;

import com.microecom.inventoryservice.model.data.NewReservation;
import com.microecom.inventoryservice.model.data.reservation.ReservedProduct;

import java.util.Set;

public class NewOrderReservation implements NewReservation {
    private final String orderId;

    private final Set<ReservedProduct> reserved;

    public NewOrderReservation(String orderId, Set<ReservedProduct> reserved) {
        this.orderId = orderId;
        this.reserved = reserved;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public Set<ReservedProduct> getReserved() {
        return reserved;
    }
}
