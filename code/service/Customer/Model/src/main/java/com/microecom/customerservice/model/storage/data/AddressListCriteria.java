package com.microecom.customerservice.model.storage.data;

import java.util.Optional;

/**
 * Criteria for retrieving a list of addresses.
 */
public interface AddressListCriteria {
    public Optional<String> getAddressLineEq();

    public Optional<String> getAddressLine2Eq();

    public Optional<Boolean> getAddressLine2IsNull();

    public Optional<Integer> getZipCodeEq();
}
