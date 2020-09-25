package com.microecom.customerservice.model;

import com.microecom.customerservice.model.data.CustomerUpdate;
import com.microecom.customerservice.model.data.ExistingCustomer;
import com.microecom.customerservice.model.exception.InvalidCustomerDataException;
import com.microecom.customerservice.model.exception.NotFoundException;

import java.util.Optional;

/**
 * Manages customers.
 */
public interface CustomerManager {
    Optional<ExistingCustomer> findByUserId(String id);

    Optional<ExistingCustomer> findById(String id);

    ExistingCustomer update(CustomerUpdate update) throws InvalidCustomerDataException, NotFoundException;

    void delete(String id);
}
