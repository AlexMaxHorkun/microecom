package com.microecom.orderservice.http.model.principal;

import com.microecom.orderservice.http.model.PrincipalManager;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component("jwtManager")
public class JwtAwarePrincipalManager implements PrincipalManager {
    public JwtAwarePrincipalManager() {}

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
}
