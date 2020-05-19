package com.microecom.orderservice.model.client;

public class RestCardPaymentRequest {
    private String orderId;

    private String customerId;

    private String cardNumbers;

    private Integer expiresMonth;

    private Integer expiresYear;

    private String cvv;

    public RestCardPaymentRequest(String orderId, String customerId, String cardNumbers, Integer expiresMonth, Integer expiresYear, String cvv) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.cardNumbers = cardNumbers;
        this.expiresMonth = expiresMonth;
        this.expiresYear = expiresYear;
        this.cvv = cvv;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCardNumbers() {
        return cardNumbers;
    }

    public void setCardNumbers(String cardNumbers) {
        this.cardNumbers = cardNumbers;
    }

    public Integer getExpiresMonth() {
        return expiresMonth;
    }

    public void setExpiresMonth(Integer expiresMonth) {
        this.expiresMonth = expiresMonth;
    }

    public Integer getExpiresYear() {
        return expiresYear;
    }

    public void setExpiresYear(Integer expiresYear) {
        this.expiresYear = expiresYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
