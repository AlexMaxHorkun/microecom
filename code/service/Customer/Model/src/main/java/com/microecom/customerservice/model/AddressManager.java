package com.microecom.customerservice.model;

import com.microecom.customerservice.model.data.Address;
import com.microecom.customerservice.model.data.AddressUpdate;
import com.microecom.customerservice.model.data.ExistingAddress;

import java.util.List;
import java.util.Optional;

/**
 * Manages customers' addresses.
 */
public interface AddressManager {
    ExistingAddress create(Address newAddress) throws IllegalArgumentException;

    ExistingAddress update(AddressUpdate update) throws IllegalArgumentException;

    void delete(String id) throws IllegalArgumentException;

    List<ExistingAddress> findListFor(String customerId) throws IllegalArgumentException;

    Optional<ExistingAddress> findById(String id);
}
