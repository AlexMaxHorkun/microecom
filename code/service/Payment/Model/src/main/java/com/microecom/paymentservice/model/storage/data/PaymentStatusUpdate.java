package com.microecom.paymentservice.model.storage.data;

import java.util.Optional;

public class PaymentStatusUpdate implements PaymentUpdate {
    private final String paymentId;

    private final Boolean posted;

    private final Integer code;

    private final String details;

    public PaymentStatusUpdate(String paymentId, Boolean posted, Integer code, String details) {
        this.paymentId = paymentId;
        this.posted = posted;
        this.code = code;
        this.details = details;
    }

    @Override
    public String getForPaymentId() {
        return paymentId;
    }

    @Override
    public Optional<Boolean> wasPosted() {
        return Optional.of(posted);
    }

    @Override
    public Optional<Integer> getProcessedCode() {
        return Optional.of(code);
    }

    @Override
    public Optional<String> getProcessedDetails() {
        return Optional.ofNullable(details);
    }
}
