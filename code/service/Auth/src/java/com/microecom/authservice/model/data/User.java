package com.microecom.authservice.model.data;

import javax.validation.constraints.NotNull;

/**
 * User entity
 */
public interface User extends NewUser {
    /**
     * User ID.
     *
     * Is just String so that ID impl not enforced.
     */
    @NotNull
    public String getId();
}
