package com.microecom.authservice.http.model;

import com.microecom.authservice.http.data.StoredUserDetails;
import com.microecom.authservice.model.UserManager;
import com.microecom.util.faultinjection.aop.annotation.InjectDelay;
import com.microecom.util.faultinjection.aop.annotation.InjectExceptionBefore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsRetriever implements UserDetailsService {
    private final UserManager userManager;

    public UserDetailsRetriever(@Autowired UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    @InjectExceptionBefore
    @InjectDelay
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var found = userManager.findByLogin(s);
        if (found.isEmpty()) {
            throw new UsernameNotFoundException("User was not found");
        }

        return new StoredUserDetails(found.get());
    }
}
