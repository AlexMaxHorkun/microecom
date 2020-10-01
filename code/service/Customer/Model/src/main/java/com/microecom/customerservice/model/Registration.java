package com.microecom.customerservice.model;

import com.microecom.customerservice.model.data.ExistingCustomer;
import com.microecom.customerservice.model.data.SigningUp;
import com.microecom.customerservice.model.exception.InvalidCustomerDataException;

/**
 * Registration service for users managed by customer service.
 */
public interface Registration {
    /**
     * Register new customer with local credentials.
     *
     * Must be idempotent.
     *
     * @param newCustomer Customer data.
     * @return Created entity.
     * @throws InvalidCustomerDataException When invalid data provided.
     */
    ExistingCustomer register(SigningUp newCustomer) throws InvalidCustomerDataException;
}
