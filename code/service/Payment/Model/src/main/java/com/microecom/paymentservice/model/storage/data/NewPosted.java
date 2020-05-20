package com.microecom.paymentservice.model.storage.data;

public class NewPosted implements NewPayment {
    private final String orderId;

    private final String customerId;

    private final Double amount;

    public NewPosted(String orderId, String customerId, Double amount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public String getCustomerId() {
        return customerId;
    }

    @Override
    public Double getAmount() {
        return amount;
    }
}
