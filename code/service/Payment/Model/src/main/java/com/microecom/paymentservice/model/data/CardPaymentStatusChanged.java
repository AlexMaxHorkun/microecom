package com.microecom.paymentservice.model.data;

import java.util.Optional;

public class CardPaymentStatusChanged implements StatusChange {
    private final String paymentId;

    private final Boolean success;

    public CardPaymentStatusChanged(String paymentId, Boolean success) {
        this.paymentId = paymentId;
        this.success = success;
    }

    @Override
    public String getForPaymentId() {
        return paymentId;
    }

    @Override
    public Boolean wasSuccessful() {
        return success;
    }

    @Override
    public Optional<String> getDetails() {
        return Optional.of("Card was declined");
    }

    @Override
    public Integer getCode() {
        return success ? 1 : 0;
    }
}
