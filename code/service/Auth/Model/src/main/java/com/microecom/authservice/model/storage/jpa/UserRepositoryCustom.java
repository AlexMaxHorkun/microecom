package com.microecom.authservice.model.storage.jpa;

import com.microecom.authservice.model.storage.jpa.data.UserRow;

public interface UserRepositoryCustom {
    public UserRow createLocal(UserRow user);
}
