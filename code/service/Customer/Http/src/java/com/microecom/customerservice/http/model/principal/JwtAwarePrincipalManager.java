package com.microecom.customerservice.http.model.principal;

import com.microecom.customerservice.http.model.PrincipalManager;
import com.microecom.customerservice.model.CustomerManager;
import com.microecom.customerservice.model.data.ExistingCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component("jwtManager")
public class JwtAwarePrincipalManager implements PrincipalManager {
    private final CustomerManager customers;

    public JwtAwarePrincipalManager(@Autowired CustomerManager customers) {
        this.customers = customers;
    }

    @Override
    public String extractUserId(Principal principal) throws SecurityException {
        if (!(principal instanceof JwtAuthenticationToken)) {
            throw new SecurityException("Wrong type of auth data provided");
        }
        var jwt = (JwtAuthenticationToken) principal;
        if (!jwt.getTokenAttributes().containsKey("user_id")) {
            throw new SecurityException("Invalid token provided");
        }

        return (String)jwt.getTokenAttributes().get("user_id");
    }

    @Override
    public Optional<ExistingCustomer> extractCustomer(Principal principal) throws SecurityException {
        return customers.findByUserId(extractUserId(principal));
    }
}
