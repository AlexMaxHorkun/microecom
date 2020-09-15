package com.microecom.authservice.model.data;

/**
 * User that has credentials
 */
public interface UserWithLogin extends NewUser {
    String getLogin();
}
