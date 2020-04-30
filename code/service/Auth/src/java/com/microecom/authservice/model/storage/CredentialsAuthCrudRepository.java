package com.microecom.authservice.model.storage;

import com.microecom.authservice.model.storage.data.CredentialsAuthRow;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialsAuthCrudRepository extends CrudRepository<CredentialsAuthRow, String> {
    public Optional<CredentialsAuthRow> findByLogin(String login);
}
