package com.microecom.customerservice.http.model.principal;

import com.microecom.customerservice.http.model.PrincipalManager;
import com.microecom.customerservice.model.data.ExistingCustomer;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

public class CompositePrincipalManager implements PrincipalManager {
    private final Set<PrincipalManager> managers;

    public CompositePrincipalManager(Set<PrincipalManager> managers) {
        this.managers = managers;
    }

    @Override
    public String extractUserId(Principal principal) throws SecurityException {
        PrincipalManager m;
        while (managers.iterator().hasNext()) {
            m = managers.iterator().next();
            try {
                return m.extractUserId(principal);
            } catch (SecurityException exception) {
                continue;
            }
        }

        throw new SecurityException("Failed to retrieve user");
    }

    @Override
    public Optional<ExistingCustomer> extractCustomer(Principal principal) throws SecurityException {
        PrincipalManager m;
        while (managers.iterator().hasNext()) {
            m = managers.iterator().next();
            try {
                return m.extractCustomer(principal);
            } catch (SecurityException exception) {
                continue;
            }
        }

        throw new SecurityException("Failed to retrieve customer");
    }
}
