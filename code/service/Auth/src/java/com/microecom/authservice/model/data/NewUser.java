package com.microecom.authservice.model.data;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * New user to be created.
 */
public interface NewUser {

    /**
     * Created time in UTC.
     */
    @NotNull ZonedDateTime getCreated();
}
