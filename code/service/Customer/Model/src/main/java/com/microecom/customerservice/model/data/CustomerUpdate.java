package com.microecom.customerservice.model.data;

import java.util.Optional;

/**
 * An update for a customer.
 */
public interface CustomerUpdate {
    String getForId();

    Optional<String> getEmail();

    Optional<String> getFirstName();

    Optional<String> getLastName();

    Optional<String> getDefaultShippingAddress();

    Optional<String> getDefaultBillingAddress();
}
