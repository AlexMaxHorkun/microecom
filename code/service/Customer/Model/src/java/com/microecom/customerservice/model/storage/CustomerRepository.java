package com.microecom.customerservice.model.storage;

import com.microecom.customerservice.model.data.Customer;
import com.microecom.customerservice.model.data.ExistingCustomer;

import java.util.Optional;

/**
 * Repo for customer objects.
 */
public interface CustomerRepository {
    ExistingCustomer save(Customer customer);

    Optional<ExistingCustomer> findByUserId(String id);
}
