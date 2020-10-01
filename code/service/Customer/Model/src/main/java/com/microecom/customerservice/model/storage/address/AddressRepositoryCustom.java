package com.microecom.customerservice.model.storage.address;

import com.microecom.customerservice.model.exception.InvalidInputDataException;
import com.microecom.customerservice.model.storage.address.data.AddressRow;
import com.microecom.customerservice.model.storage.data.AddressListCriteria;

import java.util.List;
import java.util.UUID;

/**
 * Contains methods that cannot be autogenerated for the CRUD repository.
 */
public interface AddressRepositoryCustom {
    List<AddressRow> findByCustomerId(UUID customerId, AddressListCriteria criteria) throws InvalidInputDataException;
}
