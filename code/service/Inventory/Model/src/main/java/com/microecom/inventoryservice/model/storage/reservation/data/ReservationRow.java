package com.microecom.inventoryservice.model.storage.reservation.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservations")
public class ReservationRow {
    @Id
    private UUID id;

    private UUID orderId;

    private UUID productId;

    private Integer number;

    private Boolean fulfilled;

    private Instant created;

    public ReservationRow(UUID orderId, UUID productId, Integer number) {
        this.orderId = orderId;
        this.productId = productId;
        this.number = number;
        this.id = UUID.randomUUID();
        this.fulfilled = false;
        this.created = ZonedDateTime.now(ZoneOffset.UTC).toInstant();
    }

    public ReservationRow() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean getFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(Boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}
