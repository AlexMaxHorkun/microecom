package com.microecom.orderservice.http.model.principal;

import com.microecom.orderservice.http.model.PrincipalManager;

import java.security.Principal;
import java.util.Set;

public class CompositePrincipalManager implements PrincipalManager {
    private final Set<PrincipalManager> managers;

    public CompositePrincipalManager(Set<PrincipalManager> managers) {
        this.managers = managers;
    }

    @Override
    public String extractUserId(Principal principal) throws SecurityException {
        for (PrincipalManager m : managers) {
            try {
                return m.extractUserId(principal);
            } catch (SecurityException exception) {
                continue;
            }
        }

        throw new SecurityException("Failed to retrieve user");
    }
}
