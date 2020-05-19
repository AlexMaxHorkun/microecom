package com.microecom.orderservice.model;

import com.microecom.orderservice.model.data.NewPayment;
import com.microecom.orderservice.model.exception.InvalidPaymentDetailsException;

/**
 * Client for Payment service.
 */
public interface PaymentServiceClient {
    void post(NewPayment payment) throws InvalidPaymentDetailsException;
}
