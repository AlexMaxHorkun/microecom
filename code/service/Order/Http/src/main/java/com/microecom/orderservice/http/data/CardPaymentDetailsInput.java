package com.microecom.orderservice.http.data;

import javax.validation.constraints.*;

public class CardPaymentDetailsInput {
    private String card;

    private Integer expiresMonth;

    private Integer expiresYear;

    private String cvv;

    public CardPaymentDetailsInput(String card, Integer expiresMonth, Integer expiresYear, String cvv) {
        this.card = card;
        this.expiresMonth = expiresMonth;
        this.expiresYear = expiresYear;
        this.cvv = cvv;
    }

    public CardPaymentDetailsInput() {}

    public @NotEmpty @Pattern(regexp = "^[0-9]{16}$") String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public @NotNull @Min(1) @Max(12) Integer getExpiresMonth() {
        return expiresMonth;
    }

    public void setExpiresMonth(Integer expiresMonth) {
        this.expiresMonth = expiresMonth;
    }

    public @NotNull @Min(2020) @Max(2030) Integer getExpiresYear() {
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
