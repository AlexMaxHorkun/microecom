package com.microecom.paymentservice.model.data;

abstract public class AbstractPayment implements Payment {
    private final String orderId;

    private final String customerId;

    private final Double amount;

    public AbstractPayment(String orderId, String customerId, Double amount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
    }

    @Override
    public String getForOrderId() {
        return orderId;
    }

    @Override
    public String getByCustomerId() {
        return customerId;
    }

    @Override
    public Double getAmount() {
        return amount;
    }
}
