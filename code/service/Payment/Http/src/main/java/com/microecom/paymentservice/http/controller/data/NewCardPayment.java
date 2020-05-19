package com.microecom.paymentservice.http.controller.data;

import javax.validation.constraints.*;

public class NewCardPayment {
    private String orderId;

    private String customerId;

    private String cardNumbers;

    private Integer expiresMonth;

    private Integer expiresYear;

    private String cvv;

    public NewCardPayment(String orderId, String customerId, String cardNumbers, Integer expiresMonth, Integer expiresYear, String cvv) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.cardNumbers = cardNumbers;
        this.expiresMonth = expiresMonth;
        this.expiresYear = expiresYear;
        this.cvv = cvv;
    }

    public @NotEmpty String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public @NotEmpty String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public @Pattern(regexp = "^[0-9]{16}$") String getCardNumbers() {
        return cardNumbers;
    }

    public void setCardNumbers(String cardNumbers) {
        this.cardNumbers = cardNumbers;
    }

    public @Min(1) @Max(12) Integer getExpiresMonth() {
        return expiresMonth;
    }

    public void setExpiresMonth(Integer expiresMonth) {
        this.expiresMonth = expiresMonth;
    }

    public @Min(2020) @Max(2030) Integer getExpiresYear() {
        return expiresYear;
    }

    public void setExpiresYear(Integer expiresYear) {
        this.expiresYear = expiresYear;
    }

    public @NotEmpty @Pattern(regexp = "^[0-9]{3}$") String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
