package com.microecom.paymentservice.http.controller.data;

import javax.validation.constraints.*;

public class NewCardPayment {
    private String orderId;

    private String customerId;

    private String cardNumbers;

    private Integer expiresMonth;

    private Integer expiresYear;

    private String cvv;

    private Double amount;

    public NewCardPayment(String orderId, String customerId, Double amount, String cardNumbers, Integer expiresMonth, Integer expiresYear, String cvv) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.cardNumbers = cardNumbers;
        this.expiresMonth = expiresMonth;
        this.expiresYear = expiresYear;
        this.cvv = cvv;
        this.amount = amount;
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

    public @NotNull @DecimalMin("0.1") @DecimalMax("10000000.0") Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
