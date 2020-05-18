package com.microecom.paymentservice.model.data;

import java.util.Optional;

/**
 * Status change for a payment.
 */
public interface StatusChange {
    String getForPaymentId();

    Boolean wasSuccessful();

    Optional<String> getDetails();

    Integer getCode();
}
