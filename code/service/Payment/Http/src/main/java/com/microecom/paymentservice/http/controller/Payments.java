package com.microecom.paymentservice.http.controller;

import com.microecom.paymentservice.http.controller.data.NewCardPayment;
import com.microecom.paymentservice.model.PaymentInitiator;
import com.microecom.paymentservice.model.data.CardPayment;
import com.microecom.paymentservice.model.exception.InvalidPaymentDetailsException;
import com.microecom.paymentservice.model.exception.InvalidPaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/rest/V1/payment")
public class Payments {
    private final PaymentInitiator initiator;

    public Payments(@Autowired PaymentInitiator initiator) {
        this.initiator = initiator;
    }

    @PostMapping("/card")
    public ResponseEntity<Object> create(@Valid @NotNull NewCardPayment payment) {
        try {
            initiator.initiate(new CardPayment(payment.getOrderId(), payment.getCustomerId(), payment.getAmount(), payment.getCardNumbers(), payment.getExpiresMonth(), payment.getExpiresYear(), payment.getCvv()));
        } catch (InvalidPaymentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data", e);
        } catch (InvalidPaymentDetailsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid card data", e);
        }

        return ResponseEntity.noContent().build();
    }
}
