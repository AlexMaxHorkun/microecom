package com.microecom.inventoryservice.model.storage.reservation.data;

import com.microecom.inventoryservice.model.data.ActiveReservation;

import java.time.Instant;

public class Active implements ActiveReservation {
    private final Boolean fulfilled;

    private final Instant created;

    private final String id;

    private final String orderId;

    private final String productId;

    private final Integer number;

    public Active(Boolean fulfilled, Instant created, String id, String orderId, String productId, Integer number) {
        this.fulfilled = fulfilled;
        this.created = created;
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.number = number;
    }

    @Override
    public Boolean isFulfilled() {
        return fulfilled;
    }

    @Override
    public Instant getCreated() {
        return created;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public String getProductId() {
        return productId;
    }

    @Override
    public Integer getNumber() {
        return number;
    }
}
