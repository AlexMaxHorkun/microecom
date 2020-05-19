package com.microecom.orderservice.model.data.payment;

import com.microecom.orderservice.model.data.PaymentDetails;

public class CardPaymentDetails implements PaymentDetails {
    private final String card;

    private final Integer expiresMonth;

    private final Integer expiresYear;

    private final String cvv;

    public CardPaymentDetails(String card, Integer expiresMonth, Integer expiresYear, String cvv) {
        this.card = card;
        this.expiresMonth = expiresMonth;
        this.expiresYear = expiresYear;
        this.cvv = cvv;
    }

    public String getCard() {
        return card;
    }

    public Integer getExpiresMonth() {
        return expiresMonth;
    }

    public Integer getExpiresYear() {
        return expiresYear;
    }

    public String getCvv() {
        return cvv;
    }
}
