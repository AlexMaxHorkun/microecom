package com.microecom.authservice.model.storage;

import com.microecom.authservice.model.storage.data.CredentialsAuthRow;
import org.springframework.data.repository.CrudRepository;

public interface CredentialsAuthCrudRepository extends CrudRepository<CredentialsAuthRow, String> {
}
