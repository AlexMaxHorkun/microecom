package com.microecom.authservice.model.data;

/**
 * User entity
 */
public interface User extends NewUser {
    /**
     * User ID.
     *
     * Is just String so that ID impl not enforced.
     */
    String getId();
}
