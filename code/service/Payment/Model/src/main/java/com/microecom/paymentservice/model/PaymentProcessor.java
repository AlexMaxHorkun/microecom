package com.microecom.paymentservice.model;

import com.microecom.paymentservice.model.data.Payment;
import com.microecom.paymentservice.model.exception.InvalidPaymentDetailsException;

/**
 * Processes payments.
 */
public interface PaymentProcessor {
    void post(Payment paymentInfo, String paymentId) throws InvalidPaymentDetailsException;

    boolean canProcess(Payment payment);
}
