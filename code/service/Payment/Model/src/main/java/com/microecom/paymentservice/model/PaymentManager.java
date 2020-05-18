package com.microecom.paymentservice.model;

import com.microecom.paymentservice.model.data.Payment;
import com.microecom.paymentservice.model.data.StatusChange;
import com.microecom.paymentservice.model.exception.InvalidPaymentDetailsException;
import com.microecom.paymentservice.model.exception.PaymentInfoNotFoundException;

/**
 * Manages payments.
 */
public interface PaymentManager {
    void start(Payment payment) throws InvalidPaymentDetailsException;

    void updateStatus(StatusChange change) throws PaymentInfoNotFoundException;
}
