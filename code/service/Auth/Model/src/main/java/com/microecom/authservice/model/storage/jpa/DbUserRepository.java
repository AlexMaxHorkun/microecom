package com.microecom.authservice.model.storage.jpa;

import com.microecom.authservice.model.data.*;
import com.microecom.authservice.model.storage.exception.NotFoundException;
import com.microecom.authservice.model.storage.exception.NotUniqueException;
import com.microecom.authservice.model.storage.UserRepository;
import com.microecom.authservice.model.storage.jpa.data.CredentialsAuthRow;
import com.microecom.authservice.model.storage.data.UserData;
import com.microecom.authservice.model.storage.jpa.data.UserRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * User repository utilizing database.
 */
@Component
public class DbUserRepository implements UserRepository {
    private final UserCrudRepository userRepo;

    private final CredentialsAuthCrudRepository credentialsRepo;

    public DbUserRepository(
            @Autowired UserCrudRepository userRepo,
            @Autowired CredentialsAuthCrudRepository credentialsRepo
    ) {
        this.userRepo = userRepo;
        this.credentialsRepo = credentialsRepo;
    }

    @Transactional
    @Override
    public User create(NewUserWithCredentials user) {
        try {
            UserRow saved = userRepo.save(new UserRow(user.getCreated().toInstant()));
            credentialsRepo.save(new CredentialsAuthRow(saved, user.getLogin(), user.getPassword()));

            return createUserReadDTO(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new NotUniqueException(ex);
        }
    }

    @Override
    public Optional<User> fetchById(String id) {
        Optional<UserRow> found = userRepo.findById(UUID.fromString(id));
        Optional<User> returning = Optional.empty();
        if (found.isPresent()) {
            returning = Optional.of(createUserReadDTO(found.get()));
        }

        return returning;
    }

    @Transactional
    @Override
    public void update(UserWithCredentials user) {
        var found = userRepo.findById(UUID.fromString(user.getId()));
        if (found.isEmpty() || found.get().getCredentialsAuthRow().isEmpty()) {
            throw new NotFoundException("User row with ID " + user.getId() + " not found");
        }
        var credentials = found.get().getCredentialsAuthRow().get();
        credentials.setPassword(user.getPasswordHash());
        credentialsRepo.save(credentials);
    }

    @Transactional
    @Override
    public void delete(String id) throws IllegalArgumentException {
        try {
            userRepo.deleteById(UUID.fromString(id));
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("User with given ID was not found");
        }
    }

    @Override
    public Optional<User> fetchSensitiveById(String id) {
        Optional<UserRow> found = userRepo.findById(UUID.fromString(id));
        Optional<User> returning = Optional.empty();
        if (found.isPresent()) {
            returning = Optional.of(createUserSensitiveReadDTO(found.get()));
        }

        return returning;
    }

    @Override
    public Optional<UserWithCredentials> findByLogin(String login) {
        Optional<UserWithCredentials> result = Optional.empty();
        var found = credentialsRepo.findByLogin(login);
        if (found.isPresent()) {
            result = Optional.of((UserWithCredentials)createUserSensitiveReadDTO(found.get().getUser()));
        }

        return result;
    }

    private User createUserReadDTO(UserRow userRow) {
        if (userRow.getCredentialsAuthRow().isPresent()) {
            return new UserWithCredentialsRead(
                    userRow.getId().toString(),
                    userRow.getCreated(),
                    userRow.getCredentialsAuthRow().get().getLogin()
            );
        } else {
            return new UserData(userRow.getId().toString(), userRow.getCreated());
        }
    }

    private User createUserSensitiveReadDTO(UserRow userRow) {
        if (userRow.getCredentialsAuthRow().isPresent()) {
            return new UserWithCredentialsData(
                    userRow.getId().toString(),
                    userRow.getCreated(),
                    userRow.getCredentialsAuthRow().get().getLogin(),
                    userRow.getCredentialsAuthRow().get().getPassword()
            );
        } else {
            return new UserData(userRow.getId().toString(), userRow.getCreated());
        }
    }
}
