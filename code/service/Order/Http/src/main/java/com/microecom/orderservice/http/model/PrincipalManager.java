package com.microecom.orderservice.http.model;

import java.security.Principal;

/**
 * Manages current principal.
 */
public interface PrincipalManager {
    String extractUserId(Principal principal) throws SecurityException;
}
