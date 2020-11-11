package com.microecom.authservice.model.storage;

import com.microecom.authservice.model.data.*;
import com.microecom.authservice.model.storage.data.CustomerInfusedUser;
import com.microecom.authservice.model.storage.data.UserCustomerUpdate;
import com.microecom.authservice.model.storage.exception.NotFoundException;
import com.microecom.authservice.model.storage.exception.NotUniqueException;

import java.util.Optional;
import java.util.Set;

/**
 * Repository to store users.
 */
public interface UserRepository {
    User create(NewUserWithCredentials user) throws NotUniqueException;

    void update(UserWithCredentials user) throws NotFoundException;

    Optional<User> fetchById(String id);

    void delete(String id) throws NotFoundException;

    Optional<User> fetchSensitiveById(String id);

    Optional<UserWithCredentials> findByLogin(String login);

    Set<CustomerInfusedUser> infuse(UserCustomerUpdate[] updates);
}
