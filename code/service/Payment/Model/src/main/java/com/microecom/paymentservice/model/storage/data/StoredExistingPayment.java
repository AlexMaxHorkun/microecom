package com.microecom.paymentservice.model.storage.data;

import java.util.Optional;

public class StoredExistingPayment implements ExistingPayment {
    private final String id;

    private final Boolean posted;

    private final Integer code;

    private final String details;

    private final String orderId;

    private final String customerId;

    public StoredExistingPayment(String id, Boolean posted, Integer code, String details, String orderId, String customerId) {
        this.id = id;
        this.posted = posted;
        this.code = code;
        this.details = details;
        this.orderId = orderId;
        this.customerId = customerId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Optional<Boolean> wasPosted() {
        return Optional.ofNullable(posted);
    }

    @Override
    public Optional<Integer> getProcessedCode() {
        return Optional.ofNullable(code);
    }

    @Override
    public Optional<String> getProcessedDetails() {
        return Optional.ofNullable(details);
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public String getCustomerId() {
        return customerId;
    }
}
