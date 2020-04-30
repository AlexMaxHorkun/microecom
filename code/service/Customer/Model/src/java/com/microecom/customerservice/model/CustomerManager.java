package com.microecom.customerservice.model;

import com.microecom.customerservice.model.data.ExistingCustomer;

import java.util.Optional;

/**
 * Manages customers.
 */
public interface CustomerManager {
    Optional<ExistingCustomer> findByUserId(String id);
}
