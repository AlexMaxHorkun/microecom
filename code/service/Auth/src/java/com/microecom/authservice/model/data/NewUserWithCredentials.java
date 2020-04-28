package com.microecom.authservice.model.data;

import javax.validation.constraints.NotNull;

/**
 * New user with credentials to be created.
 */
public interface NewUserWithCredentials extends UserWithLogin {
    public @NotNull String getPassword();
}
