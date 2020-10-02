package com.microecom.authservice.model;

import com.microecom.authservice.model.data.*;
import com.microecom.authservice.model.exception.InvalidUserDataException;
import com.microecom.authservice.model.storage.exception.NotFoundException;
import com.microecom.authservice.model.storage.exception.NotUniqueException;
import com.microecom.authservice.model.storage.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    public User create(NewUserWithCredentials user) {
        var newUser = new NewUserData(
                user.getLogin(),
                passwordEncryptor.encrypt(user.getPassword())
        );

        try {
            return userRepo.create(newUser);
        } catch (NotUniqueException ex) {
            //Validate if a duplicate call
            var existing = findByLogin(user.getLogin());
            if (existing.isPresent()
                    && passwordEncryptor.isValid(user.getPassword(), existing.get().getPasswordHash())
            ) {
                //Duplicate call with the same login and password found
                //Return previously created entity to ensure idempotency
                return existing.get();
            }

            throw new InvalidUserDataException("Non-unique login provided");
        }
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepo.fetchById(id);
    }

    @Transactional
    @Override
    public User update(UserWithCredentialsUpdate update) {
        var found = userRepo.fetchSensitiveById(update.getUserId());
        if (found.isEmpty() || !(found.get() instanceof UserWithCredentials)) {
            throw new InvalidUserDataException("Non-existent user requested");
        }

        var user = (UserWithCredentials)found.get();
        var password = user.getPasswordHash();
        if (update.getNewPassword().isPresent()
                && !passwordEncryptor.isValid(update.getNewPassword().get(), user.getPasswordHash())) {
            //Actually new password was provided
            password = passwordEncryptor.encrypt(update.getNewPassword().get());
        }

        //Do any fields need an update?
        if (!user.getPasswordHash().equals(password)) {
            var updatedUser = new UserWithCredentialsData(
                    update.getUserId(),
                    user.getCreated().toInstant(),
                    user.getLogin(),
                    password
            );
            userRepo.update(updatedUser);

            return findById(update.getUserId()).get();
        } else {
            return user;
        }
    }

    @Override
    public void delete(String id) {
        try {
            userRepo.delete(id);
        } catch (NotFoundException ex) {
            //Not failing in case of a duplicate call for an already deleted entity.
        }
    }

    @Override
    public Optional<UserWithCredentials> findByLogin(String login) {
        return userRepo.findByLogin(login);
    }
}
