package com.microecom.customerservice.model;

import com.microecom.customerservice.model.data.Address;
import com.microecom.customerservice.model.data.AddressUpdate;
import com.microecom.customerservice.model.data.ExistingAddress;
import com.microecom.customerservice.model.exception.InvalidAddressDataException;
import com.microecom.customerservice.model.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Manages customers' addresses.
 */
public interface AddressManager {
    /**
     * Create a new address.
     *
     * Must be idempotent.
     */
    ExistingAddress create(Address newAddress) throws InvalidAddressDataException;

    ExistingAddress update(AddressUpdate update) throws InvalidAddressDataException;

    /**
     * Must not fail when entity was not found for idempotency.
     */
    void delete(String id);

    List<ExistingAddress> findListFor(String customerId) throws NotFoundException;

    Optional<ExistingAddress> findById(String id);
}
