package com.microecom.authservice.model.data;

import javax.validation.constraints.NotNull;

/**
 * User data with password hash.
 */
public interface UserWithCredentials extends UserWithLogin, User {
    @NotNull
    public String getPasswordHash();
}
