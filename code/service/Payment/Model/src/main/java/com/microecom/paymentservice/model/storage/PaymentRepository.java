package com.microecom.paymentservice.model.storage;

import com.microecom.paymentservice.model.exception.PaymentInfoNotFoundException;
import com.microecom.paymentservice.model.storage.data.ExistingPayment;
import com.microecom.paymentservice.model.storage.data.NewPayment;
import com.microecom.paymentservice.model.storage.data.PaymentUpdate;

import java.util.Optional;

public interface PaymentRepository {
    ExistingPayment create(NewPayment payment);

    Optional<ExistingPayment> findById(String id);

    ExistingPayment update(PaymentUpdate update) throws PaymentInfoNotFoundException;
}
