package com.microecom.paymentservice.model.storage.data;

import java.util.Optional;

public interface PaymentUpdate {
    String getForPaymentId();

    Optional<Boolean> wasPosted();

    Optional<Integer> getProcessedCode();

    Optional<String> getProcessedDetails();
}
