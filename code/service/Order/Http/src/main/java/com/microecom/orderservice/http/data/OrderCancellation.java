package com.microecom.orderservice.http.data;

import com.microecom.orderservice.model.data.OrderStatus;
import com.microecom.orderservice.model.data.OrderUpdate;
import com.microecom.orderservice.model.data.PaymentDetails;

import java.util.Optional;

public class OrderCancellation implements OrderUpdate {
    private final String orderId;

    public OrderCancellation(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String getForOrderId() {
        return orderId;
    }

    @Override
    public Optional<OrderStatus> getStatus() {
        return Optional.of(OrderStatus.CANCELED);
    }

    @Override
    public Optional<PaymentDetails> getPaymentDetails() {
        return Optional.empty();
    }
}
