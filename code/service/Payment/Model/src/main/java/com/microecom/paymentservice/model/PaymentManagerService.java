package com.microecom.paymentservice.model;

import com.microecom.paymentservice.eventlist.PaymentProcessedEvent;
import com.microecom.paymentservice.model.data.Event;
import com.microecom.paymentservice.model.data.Payment;
import com.microecom.paymentservice.model.data.StatusChange;
import com.microecom.paymentservice.model.exception.InvalidPaymentException;
import com.microecom.paymentservice.model.exception.PaymentInfoNotFoundException;
import com.microecom.paymentservice.model.storage.PaymentRepository;
import com.microecom.paymentservice.model.storage.data.ExistingPayment;
import com.microecom.paymentservice.model.storage.data.NewPosted;
import com.microecom.paymentservice.model.storage.data.PaymentStatusUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentManagerService implements PaymentManager {
    private final PaymentRepository repo;

    private final EventPublisher events;

    public PaymentManagerService(@Autowired PaymentRepository repo, @Autowired EventPublisher events) {
        this.repo = repo;
        this.events = events;
    }

    @Transactional
    @Override
    public String create(Payment payment) throws InvalidPaymentException {
        ExistingPayment created;
        try {
            created = repo.create(new NewPosted(payment.getForOrderId(), payment.getByCustomerId()));
        } catch (IllegalArgumentException exception) {
            throw new InvalidPaymentException("Invalid payment fields.");
        }

        return created.getId();
    }

    @Transactional
    @Override
    public void updateStatus(StatusChange change) throws PaymentInfoNotFoundException {
        String details = null;
        if (change.getDetails().isPresent()) {
            details = change.getDetails().get();
        }
        var existing = repo.update(new PaymentStatusUpdate(change.getForPaymentId(), change.wasSuccessful(), change.getCode(), details));

        events.publish(new Event("payment-processed", new PaymentProcessedEvent(existing.getId(), existing.getOrderId(), change.wasSuccessful())));
    }
}
