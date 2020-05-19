package com.microecom.paymentservice.model;

import com.microecom.paymentservice.eventlist.PaymentProcessedEvent;
import com.microecom.paymentservice.model.data.CardPayment;
import com.microecom.paymentservice.model.data.CardPaymentStatusChanged;
import com.microecom.paymentservice.model.data.Event;
import com.microecom.paymentservice.model.data.Payment;
import com.microecom.paymentservice.model.exception.InvalidPaymentDetailsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service("cardPaymentProcessor")
public class CardPaymentProcessor implements PaymentProcessor {
    private final PaymentManager manager;

    public CardPaymentProcessor(@Autowired PaymentManager manager) {
        this.manager = manager;
    }

    @Override
    public void post(Payment paymentInfo, String paymentId) throws InvalidPaymentDetailsException {
        final var card = (CardPayment) paymentInfo;
        if (!card.getCardNumbers().equals("1111222233334444") || card.getExpiresMonth() != 1 || card.getExpiresYear() != 2021) {
            throw new InvalidPaymentDetailsException("Invalid card");
        }

        final var executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(new PaymentDecider(paymentId, card, manager), 1, TimeUnit.MINUTES);
    }

    @Override
    public boolean canProcess(Payment payment) {
        return payment instanceof CardPayment;
    }

    private class PaymentDecider implements Runnable {
        private final CardPayment card;

        private final String id;

        private final PaymentManager manager;

        public PaymentDecider(String id, CardPayment card, PaymentManager manager) {
            this.id = id;
            this.card = card;
            this.manager = manager;
        }

        @Override
        public void run() {
            final var success = card.getCvv().equals("111");
            manager.updateStatus(new CardPaymentStatusChanged(id, success));
        }
    }
}
