package com.microecom.paymentservice.model.storage.data;

import java.util.Optional;

public interface ExistingPayment extends NewPayment {
    String getId();

    Optional<Boolean> wasPosted();

    Optional<Integer> getProcessedCode();

    Optional<String> getProcessedDetails();
}
