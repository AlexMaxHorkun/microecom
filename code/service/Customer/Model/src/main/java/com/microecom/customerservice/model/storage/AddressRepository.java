package com.microecom.customerservice.model.storage;

import com.microecom.customerservice.model.data.Address;
import com.microecom.customerservice.model.data.AddressUpdate;
import com.microecom.customerservice.model.data.ExistingAddress;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    ExistingAddress create(Address newAddress) throws IllegalArgumentException;

    Optional<ExistingAddress> findById(String id);

    List<ExistingAddress> findByCustomerId(String id) throws IllegalArgumentException;

    ExistingAddress update(AddressUpdate update) throws IllegalArgumentException;

    void delete(String id) throws IllegalArgumentException;
}
