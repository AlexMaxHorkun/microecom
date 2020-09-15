package com.microecom.authservice.model.storage.jpa;

import com.microecom.authservice.model.storage.jpa.data.UserRow;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserCrudRepository extends CrudRepository<UserRow, UUID> {
}
