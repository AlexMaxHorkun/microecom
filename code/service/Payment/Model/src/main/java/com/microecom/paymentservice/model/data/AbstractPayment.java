package com.microecom.paymentservice.model.data;

abstract public class AbstractPayment implements Payment {
    private final String orderId;

    private final String customerId;

    public AbstractPayment(String orderId, String customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
    }

    @Override
    public String getForOrderId() {
        return orderId;
    }

    @Override
    public String getByCustomerId() {
        return customerId;
    }
}
