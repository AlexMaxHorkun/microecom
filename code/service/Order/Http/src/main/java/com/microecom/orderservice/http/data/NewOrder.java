package com.microecom.orderservice.http.data;

import com.microecom.orderservice.model.data.PaymentDetails;
import com.microecom.orderservice.model.data.payment.CardPaymentDetails;

import java.util.Set;

public class NewOrder implements com.microecom.orderservice.model.data.NewOrder {
    private final String customerId;

    private final Set<String> productIds;

    private final PaymentDetails details;

    public static NewOrder of(NewOrderInput input, String customerId) {
        return new NewOrder(
                customerId,
                input.getProductIds(),
                new CardPaymentDetails(input.getCard().getCard(), input.getCard().getExpiresMonth(), input.getCard().getExpiresYear(), input.getCard().getCvv())
        );
    }

    private NewOrder(String customerId, Set<String> productIds, PaymentDetails details) {
        this.customerId = customerId;
        this.productIds = productIds;
        this.details = details;
    }

    @Override
    public PaymentDetails getPaymentDetails() {
        return null;
    }

    @Override
    public String getCustomerId() {
        return null;
    }

    @Override
    public Set<String> getProductIds() {
        return null;
    }
}
