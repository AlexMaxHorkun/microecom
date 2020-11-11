package com.microecom.authservice.model.storage.jpa;

import com.microecom.authservice.model.storage.data.UserCustomerUpdate;
import com.microecom.authservice.model.storage.jpa.data.UserRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private final UserCrudRepository userCrudRepo;

    private final CredentialsAuthCrudRepository credentialsCrudRepo;

    public UserRepositoryCustomImpl(
            @Autowired CredentialsAuthCrudRepository credentialsCrudRepo,
            @Autowired UserCrudRepository userCrudRepo
    ) {
        this.userCrudRepo = userCrudRepo;
        this.credentialsCrudRepo = credentialsCrudRepo;
    }

    @Transactional
    @Override
    public UserRow createLocal(UserRow user) {
        var credentials = user.getCredentialsAuthRow().get();
        user.setCredentialsAuthRow(null);
        user = userCrudRepo.save(user);
        credentials.setUser(user);
        credentials = credentialsCrudRepo.save(credentials);
        user.setCredentialsAuthRow(credentials);

        return user;
    }

    @Transactional
    @Override
    public void updateCustomerDataForAll(Set<UserCustomerUpdate> updates) {
        updates.forEach(userCrudRepo::addCustomerData);
    }
}
