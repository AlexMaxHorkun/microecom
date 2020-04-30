package com.microecom.authservice.model;

import com.microecom.authservice.model.data.NewUserWithCredentials;
import com.microecom.authservice.model.data.User;
import com.microecom.authservice.model.data.UserWithCredentials;
import com.microecom.authservice.model.data.UserWithCredentialsUpdate;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Users manager.
 */
public interface UserManager {
    User create(@NotNull NewUserWithCredentials user);

    Optional<User> findById(@NotNull String id);

    User update(@NotNull UserWithCredentialsUpdate update);

    void delete(@NotNull String id) throws IllegalArgumentException;

    Optional<UserWithCredentials> findByLogin(String login);
}
