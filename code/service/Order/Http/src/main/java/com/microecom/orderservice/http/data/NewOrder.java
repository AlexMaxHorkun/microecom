package com.microecom.orderservice.http.data;

import com.microecom.orderservice.model.data.OrderedQuantity;
import com.microecom.orderservice.model.data.PaymentDetails;
import com.microecom.orderservice.model.data.payment.CardPaymentDetails;

import java.util.HashSet;
import java.util.Set;

public class NewOrder implements com.microecom.orderservice.model.data.NewOrder {
    private final String customerId;

    private final Set<OrderedQuantity> ordered;

    private final PaymentDetails details;

    public static NewOrder of(NewOrderInput input, String customerId) {
        var ordered = new HashSet<OrderedQuantity>();
        for (OrderedInput o : input.getOrdered()) {
            ordered.add(new Ordered(o.getProductId(), o.getQuantity()));
        }

        return new NewOrder(
                customerId,
                ordered,
                new CardPaymentDetails(input.getCard().getCard(), input.getCard().getExpiresMonth(), input.getCard().getExpiresYear(), input.getCard().getCvv())
        );
    }

    private NewOrder(String customerId, Set<OrderedQuantity> ordered, PaymentDetails details) {
        this.customerId = customerId;
        this.ordered = ordered;
        this.details = details;
    }

    @Override
    public PaymentDetails getPaymentDetails() {
        return details;
    }

    @Override
    public String getCustomerId() {
        return customerId;
    }

    @Override
    public Set<OrderedQuantity> getOrdered() {
        return ordered;
    }
}
