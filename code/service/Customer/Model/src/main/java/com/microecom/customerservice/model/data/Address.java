package com.microecom.customerservice.model.data;

import java.util.Optional;

/**
 * Customer address.
 */
public interface Address {
    String getCustomerId();

    String getAddressLine();

    Optional<String> getAddressLine2();

    int getZipCode();
}
