package com.microecom.orderservice.model.data;

import java.util.Optional;

/**
 * Update data for an order.
 */
public interface OrderUpdate {
    String getForOrderId();

    Optional<OrderStatus> getStatus();

    Optional<PaymentDetails> getPaymentDetails();
}
