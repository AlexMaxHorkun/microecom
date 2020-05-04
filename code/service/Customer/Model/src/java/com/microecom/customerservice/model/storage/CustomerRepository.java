package com.microecom.customerservice.model.storage;

import com.microecom.customerservice.model.data.Customer;
import com.microecom.customerservice.model.data.CustomerUpdate;
import com.microecom.customerservice.model.data.ExistingCustomer;

import java.util.Optional;

/**
 * Repo for customer objects.
 */
public interface CustomerRepository {
    ExistingCustomer save(Customer customer);

    Optional<ExistingCustomer> findByUserId(String userId);

    Optional<ExistingCustomer> findById(String id);

    ExistingCustomer update(CustomerUpdate update) throws IllegalArgumentException;

    void delete(String id) throws IllegalArgumentException;
}
