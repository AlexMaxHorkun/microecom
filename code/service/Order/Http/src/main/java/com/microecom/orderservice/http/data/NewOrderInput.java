package com.microecom.orderservice.http.data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class NewOrderInput {
    private Set<String> productIds;

    private CardPaymentDetailsInput card;

    public NewOrderInput() {}

    public @NotNull @Size(min = 1, max = 100) Set<@NotEmpty String> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<String> productIds) {
        this.productIds = productIds;
    }

    public @Valid @NotNull CardPaymentDetailsInput getCard() {
        return card;
    }

    public void setCard(CardPaymentDetailsInput card) {
        this.card = card;
    }
}
