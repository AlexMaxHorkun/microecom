package com.microecom.authservice.model;

import com.microecom.authservice.model.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Default user manager.
 */
@Component
public class DefaultUserManager implements UserManager {
    private final UserRepository userRepo;

    private final PasswordEncryptor passwordEncryptor;

    public DefaultUserManager(@Autowired UserRepository userRepo, @Autowired PasswordEncryptor passwordEncryptor) {
        this.userRepo = userRepo;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    public User create(@NotNull NewUserWithCredentials user) {
        user = new NewUserData(
                user.getLogin(),
                passwordEncryptor.encrypt(user.getPassword())
        );
        return userRepo.create(user);
    }

    @Override
    public Optional<User> findById(@NotNull String id) {
        return userRepo.fetchById(id);
    }

    @Override
    public User update(@NotNull UserWithCredentialsUpdate update) {
        var found = userRepo.fetchSensitiveById(update.getUserId());
        if (found.isEmpty() || !(found.get() instanceof UserWithCredentials)) {
            throw new IllegalArgumentException("Wrong user ID provided");
        }

        var user = (UserWithCredentials)found.get();
        var password = user.getPasswordHash();
        if (update.getNewPassword().isPresent()) {
            password = passwordEncryptor.encrypt(update.getNewPassword().get());
        }
        var updatedUser = new UserWithCredentialsData(
                update.getUserId(),
                user.getCreated().toInstant(),
                user.getLogin(),
                password
        );
        userRepo.update(updatedUser);

        return findById(update.getUserId()).get();
    }

    @Override
    public void delete(@NotNull String id) throws IllegalArgumentException {
        userRepo.delete(id);
    }
}
