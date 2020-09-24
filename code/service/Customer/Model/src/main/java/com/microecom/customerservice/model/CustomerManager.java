package com.microecom.customerservice.model;

import com.microecom.customerservice.model.data.CustomerUpdate;
import com.microecom.customerservice.model.data.ExistingCustomer;

import java.util.Optional;

/**
 * Manages customers.
 */
public interface CustomerManager {
    Optional<ExistingCustomer> findByUserId(String id);

    Optional<ExistingCustomer> findById(String id);

    ExistingCustomer update(CustomerUpdate update) throws IllegalArgumentException;

    void delete(String id) throws IllegalArgumentException;
}
