package com.microecom.authservice.model.data;

/**
 * User data with password hash.
 */
public interface UserWithCredentials extends UserWithLogin, User {
    String getPasswordHash();
}
