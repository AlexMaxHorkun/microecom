package com.microecom.customerservice.model.data;

import java.util.Optional;

/**
 * Basic customer info.
 */
public interface CustomerInfo {
    String getFirstName();

    String getLastName();

    String getEmail();

    Optional<String> getDefaultShippingAddress();

    Optional<String> getDefaultBillingAddress();
}
