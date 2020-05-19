package com.microecom.paymentservice.model.storage.payment.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class PaymentRow {
    @Id
    private UUID id;

    private UUID orderId;

    private UUID customerId;

    private Boolean posted;

    private Integer code;

    private String details;

    public PaymentRow(UUID orderId, UUID customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.id = UUID.randomUUID();
    }

    public PaymentRow() {}

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

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Boolean getPosted() {
        return posted;
    }

    public void setPosted(Boolean posted) {
        this.posted = posted;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
