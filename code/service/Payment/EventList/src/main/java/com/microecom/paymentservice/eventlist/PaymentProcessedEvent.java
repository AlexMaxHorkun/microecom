package com.microecom.paymentservice.eventlist;

/**
 * Thrown when posted payment has been processed.
 */
public class PaymentProcessedEvent {
    private String paymentId;

    private String orderId;

    private Boolean successful;

    public PaymentProcessedEvent() { }

    public PaymentProcessedEvent(String paymentId, String orderId, Boolean successful) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.successful = successful;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }
}
