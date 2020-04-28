package com.microecom.authservice.model.storage;

import com.microecom.authservice.model.storage.data.UserRow;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserCrudRepository extends CrudRepository<UserRow, UUID> {
}
