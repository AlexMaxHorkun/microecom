package com.microecom.customerservice.model;

import com.microecom.customerservice.model.data.ExistingCustomer;
import com.microecom.customerservice.model.data.SigningUp;

/**
 * Registration service for users managed by customer service.
 */
public interface Registration {
    ExistingCustomer register(SigningUp newCustomer) throws IllegalArgumentException;
}
