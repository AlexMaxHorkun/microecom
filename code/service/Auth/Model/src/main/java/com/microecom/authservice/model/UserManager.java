package com.microecom.authservice.model;

import com.microecom.authservice.model.data.NewUserWithCredentials;
import com.microecom.authservice.model.data.User;
import com.microecom.authservice.model.data.UserWithCredentials;
import com.microecom.authservice.model.data.UserWithCredentialsUpdate;
import com.microecom.authservice.model.exception.InvalidUserDataException;

import java.util.Optional;

/**
 * Users manager.
 */
public interface UserManager {
    /**
     * Create new user with a login and a password, idempotent.
     */
    User create(NewUserWithCredentials user) throws InvalidUserDataException;

    Optional<User> findById(String id);

    User update(UserWithCredentialsUpdate update) throws InvalidUserDataException;

    /**
     * Delete a user record alongside with any authentication data.
     *
     * Idempotent, will fail silently for non-existent entity.
     */
    void delete(String id);

    Optional<UserWithCredentials> findByLogin(String login);
}
