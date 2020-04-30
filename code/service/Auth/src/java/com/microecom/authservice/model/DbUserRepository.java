package com.microecom.authservice.model;

import com.microecom.authservice.model.data.*;
import com.microecom.authservice.model.storage.CredentialsAuthCrudRepository;
import com.microecom.authservice.model.storage.UserCrudRepository;
import com.microecom.authservice.model.storage.data.CredentialsAuthRow;
import com.microecom.authservice.model.storage.data.UserRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
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

    @Override
    public User create(@NotNull NewUserWithCredentials user) {
        UserRow saved = userRepo.save(new UserRow(user.getCreated().toInstant()));
        credentialsRepo.save(
                new CredentialsAuthRow(
                        saved,
                        user.getLogin(),
                        user.getPassword()
                )
        );

        return createUserReadDTO(saved);
    }

    @Override
    public Optional<User> fetchById(@NotNull String id) {
        Optional<UserRow> found = userRepo.findById(UUID.fromString(id));
        Optional<User> returning = Optional.empty();
        if (found.isPresent()) {
            returning = Optional.of(createUserReadDTO(found.get()));
        }

        return returning;
    }

    @Override
    public void update(@NotNull UserWithCredentials user) {
        var found = userRepo.findById(UUID.fromString(user.getId()));
        if (found.isEmpty() || found.get().getCredentialsAuthRow().isEmpty()) {
            throw new IllegalArgumentException("User row with ID " + user.getId() + " not found");
        }
        var credentials = found.get().getCredentialsAuthRow().get();
        credentials.setPassword(user.getPasswordHash());
        credentialsRepo.save(credentials);
    }

    @Override
    public void delete(@NotNull String id) throws IllegalArgumentException {
        try {
            userRepo.deleteById(UUID.fromString(id));
        } catch (EmptyResultDataAccessException exception) {
            throw new IllegalArgumentException("User with given ID was not found");
        }
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

    @Override
    public Optional<User> fetchSensitiveById(@NotNull String id) {
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
}
