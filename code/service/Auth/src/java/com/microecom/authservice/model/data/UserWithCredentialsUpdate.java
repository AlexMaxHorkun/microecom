package com.microecom.authservice.model.data;

import java.util.Optional;

/**
 * Update to a user with credentials.
 */
public interface UserWithCredentialsUpdate {
    /**
     * Will not be updated if empty.
     */
    Optional<String> getNewPassword();

    String getUserId();
}
