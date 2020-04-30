package com.microecom.authservice.model;

import com.microecom.authservice.model.data.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Repository to store users.
 */
public interface UserRepository {
    public @NotNull User create(@NotNull NewUserWithCredentials user);

    public void update(@NotNull UserWithCredentials user);

    public Optional<User> fetchById(@NotNull String id);

    public void delete(@NotNull String id) throws IllegalArgumentException;

    public Optional<User> fetchSensitiveById(@NotNull String id);

    public Optional<UserWithCredentials> findByLogin(String login);
}
