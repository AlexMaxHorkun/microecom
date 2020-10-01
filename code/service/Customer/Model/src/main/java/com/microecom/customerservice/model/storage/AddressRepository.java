package com.microecom.customerservice.model.storage;

import com.microecom.customerservice.model.data.Address;
import com.microecom.customerservice.model.data.AddressUpdate;
import com.microecom.customerservice.model.data.ExistingAddress;
import com.microecom.customerservice.model.exception.InvalidAddressDataException;
import com.microecom.customerservice.model.exception.InvalidInputDataException;
import com.microecom.customerservice.model.exception.NotFoundException;
import com.microecom.customerservice.model.storage.data.AddressListCriteria;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    ExistingAddress create(Address newAddress) throws InvalidAddressDataException;

    Optional<ExistingAddress> findById(String id);

    List<ExistingAddress> findByCustomerId(String id) throws NotFoundException;

    List<ExistingAddress> findByCustomerId(String id, AddressListCriteria criteria) throws NotFoundException, InvalidInputDataException;

    ExistingAddress update(AddressUpdate update) throws InvalidAddressDataException;

    void delete(String id) throws NotFoundException;
}
