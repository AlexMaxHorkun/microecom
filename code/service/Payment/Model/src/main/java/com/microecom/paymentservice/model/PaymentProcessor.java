package com.microecom.paymentservice.model;

import com.microecom.paymentservice.model.data.Payment;
import com.microecom.paymentservice.model.exception.InvalidPaymentDetailsException;

/**
 * Processes payments.
 */
public interface PaymentProcessor {
    void post(Payment payment) throws InvalidPaymentDetailsException;

    boolean canProcess(Payment payment);
}
