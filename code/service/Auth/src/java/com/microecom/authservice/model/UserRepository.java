package com.microecom.authservice.model;

import com.microecom.authservice.model.data.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Repository to store users.
 */
public interface UserRepository {
    @NotNull User create(@NotNull NewUserWithCredentials user);

    void update(@NotNull UserWithCredentials user);

    Optional<User> fetchById(@NotNull String id);

    void delete(@NotNull String id) throws IllegalArgumentException;

    Optional<User> fetchSensitiveById(@NotNull String id);

    Optional<UserWithCredentials> findByLogin(String login);
}
