package com.microecom.customerservice.model.data;

import java.time.Instant;

/**
 * Persisted customer.
 */
public interface ExistingCustomer extends Customer {
    String getId();

    Instant getSince();
}
