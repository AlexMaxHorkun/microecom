package com.microecom.customerservice.model.client;

import com.microecom.customerservice.model.client.data.NewUser;
import com.microecom.customerservice.model.client.data.User;

import java.util.Optional;

/**
 * Client for Auth service.
 */
public interface AuthClient {
    User create(NewUser user) throws IllegalArgumentException;

    Optional<User> get(String id);

    void delete(String id) throws IllegalArgumentException;
}
