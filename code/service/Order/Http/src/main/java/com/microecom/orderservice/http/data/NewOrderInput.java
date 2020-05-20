package com.microecom.orderservice.http.data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class NewOrderInput {
    private Set<OrderedInput> ordered;

    private CardPaymentDetailsInput card;

    public NewOrderInput() {}

    public @Valid @NotNull CardPaymentDetailsInput getCard() {
        return card;
    }

    public void setCard(CardPaymentDetailsInput card) {
        this.card = card;
    }

    public @NotNull @Size(min = 1) Set<@Valid OrderedInput> getOrdered() {
        return ordered;
    }

    public void setOrdered(Set<OrderedInput> ordered) {
        this.ordered = ordered;
    }
}
