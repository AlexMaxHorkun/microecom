package com.microecom.authservice.model.data;

/**
 * New user with credentials to be created.
 */
public interface NewUserWithCredentials extends UserWithLogin {
    String getPassword();
}
