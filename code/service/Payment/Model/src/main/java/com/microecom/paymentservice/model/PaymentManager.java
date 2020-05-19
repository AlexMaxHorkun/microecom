package com.microecom.paymentservice.model;

import com.microecom.paymentservice.model.data.Payment;
import com.microecom.paymentservice.model.data.StatusChange;
import com.microecom.paymentservice.model.exception.InvalidPaymentException;
import com.microecom.paymentservice.model.exception.PaymentInfoNotFoundException;

/**
 * Manages payments.
 */
public interface PaymentManager {
    String create(Payment payment) throws InvalidPaymentException;

    void updateStatus(StatusChange change) throws PaymentInfoNotFoundException;
}
