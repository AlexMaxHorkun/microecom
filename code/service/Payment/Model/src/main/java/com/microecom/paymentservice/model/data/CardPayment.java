package com.microecom.paymentservice.model.data;

public class CardPayment extends AbstractPayment {
    private final String cardNumbers;

    private final Integer expiresMonth;

    private final Integer expiresYear;

    private final String cvv;

    public CardPayment(String orderId, String customerId, Double amount, String cardNumbers, Integer expiresMonth, Integer expiresYear, String cvv) {
        super(orderId, customerId, amount);
        this.cardNumbers = cardNumbers;
        this.expiresMonth = expiresMonth;
        this.expiresYear = expiresYear;
        this.cvv = cvv;
    }

    public String getCardNumbers() {
        return cardNumbers;
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
