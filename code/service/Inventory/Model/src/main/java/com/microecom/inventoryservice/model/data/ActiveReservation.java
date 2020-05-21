package com.microecom.inventoryservice.model.data;

import java.time.Instant;

/**
 * Active reservation for a product.
 */
public interface ActiveReservation {
    Boolean isFulfilled();

    Instant getCreated();

    String getId();

    String getOrderId();

    String getProductId();

    Integer getNumber();
}
