package com.microecom.authservice.model.data;

import javax.validation.constraints.NotNull;

/**
 * User that has credentials
 */
public interface UserWithLogin extends NewUser {
    @NotNull
    public String getLogin();
}
