package com.microecom.paymentservice.model.exception;

public class PaymentInfoNotFoundException extends IllegalArgumentException {
    public PaymentInfoNotFoundException() {
        super("Payment not found");
    }
}
