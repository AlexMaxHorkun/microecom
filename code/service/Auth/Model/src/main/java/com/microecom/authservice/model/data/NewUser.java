package com.microecom.authservice.model.data;

import java.time.ZonedDateTime;

/**
 * New user to be created.
 */
public interface NewUser {

    /**
     * Created time in UTC.
     */
    ZonedDateTime getCreated();
}
