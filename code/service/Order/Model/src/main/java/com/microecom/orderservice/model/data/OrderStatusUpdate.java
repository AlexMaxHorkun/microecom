package com.microecom.orderservice.model.data;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class OrderStatusUpdate implements OrderUpdate {
    private final String orderId;

    private final OrderStatus status;

    public OrderStatusUpdate(@NotNull String orderId, @NotNull OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }

    @Override
    public String getForOrderId() {
        return orderId;
    }

    @Override
    public Optional<OrderStatus> getStatus() {
        return Optional.of(status);
    }

    @Override
    public Optional<PaymentDetails> getPaymentDetails() {
        return Optional.empty();
    }
}
