package com.microecom.orderservice.model.data;

/**
 * New payment to be posted on payment service.
 */
public class NewPayment {
    private final String orderId;

    private final String customerId;

    private final PaymentDetails payment;

    private final Double amount;

    public NewPayment(String orderId, String customerId, PaymentDetails payment, Double amount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.payment = payment;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public PaymentDetails getPayment() {
        return payment;
    }

    public Double getAmount() {
        return amount;
    }
}
