package com.microecom.paymentservice.model.storage.data;

public class NewPosted implements NewPayment {
    private final String orderId;

    private final String customerId;

    public NewPosted(String orderId, String customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public String getCustomerId() {
        return customerId;
    }
}
