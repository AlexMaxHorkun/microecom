package com.microecom.authservice;

import com.microecom.authservice.model.data.UserWithCredentials;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

public class StoredUserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private final UserWithCredentials user;

    public StoredUserDetails(UserWithCredentials user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var list = new HashSet<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority("USER_CUSTOMER"));

        return list;
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getUserId() {
        return user.getId();
    }
}
