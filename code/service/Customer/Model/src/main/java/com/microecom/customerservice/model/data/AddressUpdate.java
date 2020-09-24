package com.microecom.customerservice.model.data;

import java.util.Optional;

/**
 * Update to a customer's address.
 */
public interface AddressUpdate {
    String getForId();

    Optional<String> getAddressLine();

    Optional<String> getAddressLine2();

    Optional<Integer> getZipCode();
}
