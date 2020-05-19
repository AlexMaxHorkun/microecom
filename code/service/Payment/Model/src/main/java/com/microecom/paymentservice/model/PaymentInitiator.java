package com.microecom.paymentservice.model;

import com.microecom.paymentservice.model.data.Payment;
import com.microecom.paymentservice.model.exception.InvalidPaymentDetailsException;
import com.microecom.paymentservice.model.exception.InvalidPaymentException;

public interface PaymentInitiator {
    String initiate(Payment payment) throws InvalidPaymentDetailsException, InvalidPaymentException;
}
