package com.microecom.orderservice.model.client;

import com.microecom.orderservice.model.PaymentServiceClient;
import com.microecom.orderservice.model.data.NewPayment;
import com.microecom.orderservice.model.data.payment.CardPaymentDetails;
import com.microecom.orderservice.model.exception.InvalidPaymentDetailsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestPaymentServiceClient implements PaymentServiceClient {
    private final String paymentRestUri;

    private final RestTemplate rest;

    public RestPaymentServiceClient(
            @Value("${order-service.client.payment.uri}") String paymentRestUri,
            @Autowired RestTemplateBuilder restBuilder
    ) {
        this.paymentRestUri = paymentRestUri;
        this.rest = restBuilder.build();
    }

    @Override
    public void post(NewPayment payment) throws InvalidPaymentDetailsException {
        ResponseEntity response;
        if (payment.getPayment() instanceof CardPaymentDetails) {
            response = postCard(payment.getOrderId(), payment.getCustomerId(), (CardPaymentDetails) payment.getPayment());
        } else {
            throw new IllegalArgumentException("Unknown payment details type");
        }

        if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            throw new InvalidPaymentDetailsException();
        }
        if (!response.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
            throw new RuntimeException("Failed to post payment");
        }
    }

    private ResponseEntity<Void> postCard(String orderId, String customerId, CardPaymentDetails payment) {
        return rest.postForEntity(
                paymentRestUri + "/V1/payment/card",
                new RestCardPaymentRequest(orderId, customerId, payment.getCard(), payment.getExpiresMonth(), payment.getExpiresYear(), payment.getCvv()),
                Void.class
        );
    }
}
