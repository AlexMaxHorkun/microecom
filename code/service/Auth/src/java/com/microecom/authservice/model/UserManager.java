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
    public User create(@NotNull NewUserWithCredentials user);

    public Optional<User> findById(@NotNull String id);

    public User update(@NotNull UserWithCredentialsUpdate update);

    public void delete(@NotNull String id) throws IllegalArgumentException;

    public Optional<UserWithCredentials> findByLogin(String login);
}
