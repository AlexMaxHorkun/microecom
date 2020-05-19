package com.microecom.paymentservice.model;

import com.microecom.paymentservice.model.data.Payment;
import com.microecom.paymentservice.model.exception.InvalidPaymentDetailsException;
import com.microecom.paymentservice.model.exception.InvalidPaymentException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public class PaymentInitiatorService implements PaymentInitiator {
    private final Set<PaymentProcessor> processors;

    private final PaymentManager manager;

    public PaymentInitiatorService(Set<PaymentProcessor> processors, PaymentManager manager) {
        this.processors = processors;
        this.manager = manager;
    }

    @Transactional
    @Override
    public String initiate(Payment payment) throws InvalidPaymentDetailsException, InvalidPaymentException {
        PaymentProcessor found = null;
        for (PaymentProcessor processor : processors) {
            if (processor.canProcess(payment)) {
                found = processor;
                break;
            }
        }
        if (found == null) {
            throw new InvalidPaymentDetailsException("Invalid payment details");
        }

        var id = manager.create(payment);
        found.post(payment, id);

        return id;
    }
}
