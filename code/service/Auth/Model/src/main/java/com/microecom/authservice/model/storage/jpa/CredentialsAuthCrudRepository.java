package com.microecom.authservice.model.storage.jpa;

import com.microecom.authservice.model.storage.jpa.data.CredentialsAuthRow;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialsAuthCrudRepository extends CrudRepository<CredentialsAuthRow, String> {
    Optional<CredentialsAuthRow> findByLogin(String login);
}
