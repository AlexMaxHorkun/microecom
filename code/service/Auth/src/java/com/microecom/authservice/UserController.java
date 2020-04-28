package com.microecom.authservice;

import com.microecom.authservice.model.UserManager;
import com.microecom.authservice.model.data.NewUserData;
import com.microecom.authservice.model.data.User;
import com.microecom.authservice.model.data.UserUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * User resource controller
 */
@RestController
@RequestMapping("/rest/V1/user")
@Validated
public class UserController {
    private UserManager users;

    public UserController(@Autowired UserManager users) {
        this.users = users;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @NotNull ResponseEntity<User> create(@Valid @RequestBody @NotNull NewUserData newUser) {
        return new ResponseEntity<>(users.create(newUser), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @NotNull ResponseEntity<User> get(@NotNull @PathVariable String id) {
        var found = users.findById(id);

        return found.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PATCH)
    public @NotNull ResponseEntity<User> patch(
            @Valid @RequestBody @NotNull UserUpdate update,
            @Valid @NotNull @PathVariable String userId
    ) {
        update.setUserId(userId);

        try {
            return new ResponseEntity<>(users.update(update), HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable String userId) {
        try {
            users.delete(userId);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
