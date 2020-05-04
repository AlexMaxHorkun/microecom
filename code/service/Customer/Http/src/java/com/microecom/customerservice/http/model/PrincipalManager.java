package com.microecom.customerservice.http.model;

import com.microecom.customerservice.model.data.ExistingCustomer;

import java.security.Principal;
import java.util.Optional;

/**
 * Manages current principal.
 */
public interface PrincipalManager {
    String extractUserId(Principal principal) throws SecurityException;

    Optional<ExistingCustomer> extractCustomer(Principal principal) throws SecurityException;
}
