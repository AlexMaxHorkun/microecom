package com.microecom.paymentservice.model.storage;

import com.microecom.paymentservice.model.exception.PaymentInfoNotFoundException;
import com.microecom.paymentservice.model.storage.data.ExistingPayment;
import com.microecom.paymentservice.model.storage.data.NewPayment;
import com.microecom.paymentservice.model.storage.data.PaymentUpdate;
import com.microecom.paymentservice.model.storage.data.StoredExistingPayment;
import com.microecom.paymentservice.model.storage.payment.CrudPaymentRepository;
import com.microecom.paymentservice.model.storage.payment.data.PaymentRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
public class JpaPaymentRepository implements PaymentRepository {
    private final CrudPaymentRepository repo;

    public JpaPaymentRepository(@Autowired CrudPaymentRepository repo) {
        this.repo = repo;
    }

    @Transactional
    @Override
    public ExistingPayment create(NewPayment payment) throws IllegalArgumentException {
        try {
            return convert(repo.save(new PaymentRow(UUID.fromString(payment.getOrderId()), UUID.fromString(payment.getCustomerId()), payment.getAmount())));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid payment data", e);
        }
    }

    @Override
    public Optional<ExistingPayment> findById(String id) {
        return repo.findById(UUID.fromString(id)).map(this::convert);
    }

    @Transactional
    @Override
    public ExistingPayment update(PaymentUpdate update) throws PaymentInfoNotFoundException {
        var found = repo.findById(UUID.fromString(update.getForPaymentId()));
        if (found.isEmpty()) {
            throw new PaymentInfoNotFoundException();
        }

        var row = found.get();
        if (update.wasPosted().isPresent()) {
            row.setPosted(update.wasPosted().get());
        }
        if (update.getProcessedCode().isPresent()) {
            row.setCode(update.getProcessedCode().get());
        }
        if (update.getProcessedDetails().isPresent()) {
            row.setDetails(update.getProcessedDetails().get());
        }

        return convert(repo.save(row));
    }

    private ExistingPayment convert(PaymentRow row) {
        return new StoredExistingPayment(
                row.getId().toString(),
                row.getPosted(),
                row.getCode(),
                row.getDetails(),
                row.getOrderId().toString(),
                row.getCustomerId().toString(),
                row.getAmount()
        );
    }
}
