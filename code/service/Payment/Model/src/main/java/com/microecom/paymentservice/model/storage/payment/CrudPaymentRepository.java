package com.microecom.paymentservice.model.storage.payment;

import com.microecom.paymentservice.model.storage.payment.data.PaymentRow;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CrudPaymentRepository extends CrudRepository<PaymentRow, UUID> {
}
