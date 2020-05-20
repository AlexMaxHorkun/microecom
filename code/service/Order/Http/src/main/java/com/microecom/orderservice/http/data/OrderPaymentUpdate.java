package com.microecom.orderservice.http.data;

import com.microecom.orderservice.model.data.OrderStatus;
import com.microecom.orderservice.model.data.OrderUpdate;
import com.microecom.orderservice.model.data.PaymentDetails;

import java.util.Optional;

public class OrderPaymentUpdate implements OrderUpdate {
    private final String orderId;

    private final PaymentDetails details;

    public OrderPaymentUpdate(String orderId, PaymentDetails details) {
        this.orderId = orderId;
        this.details = details;
    }

    @Override
    public String getForOrderId() {
        return orderId;
    }

    @Override
    public Optional<OrderStatus> getStatus() {
        return Optional.empty();
    }

    @Override
    public Optional<PaymentDetails> getPaymentDetails() {
        return Optional.of(details);
    }
}
