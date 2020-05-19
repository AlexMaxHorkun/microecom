package com.microecom.paymentservice.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class PaymentInitiatorFactory {
    @Bean
    public PaymentInitiator create(CardPaymentProcessor cardProcessor, PaymentManager manager) {
        var processors = new HashSet<PaymentProcessor>();
        processors.add(cardProcessor);

        return new PaymentInitiatorService(processors, manager);
    }
}
