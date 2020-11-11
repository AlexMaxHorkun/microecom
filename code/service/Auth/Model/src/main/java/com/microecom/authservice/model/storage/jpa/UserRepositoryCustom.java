package com.microecom.authservice.model.storage.jpa;

import com.microecom.authservice.model.storage.data.UserCustomerUpdate;
import com.microecom.authservice.model.storage.jpa.data.UserRow;

import java.util.Set;

public interface UserRepositoryCustom {
    public UserRow createLocal(UserRow user);

    public void updateCustomerDataForAll(UserCustomerUpdate[] updates);
}
