package com.microecom.customerservice.model.client;

import com.microecom.customerservice.model.client.auth.data.NewUserArgument;
import com.microecom.customerservice.model.client.auth.data.UserObject;
import com.microecom.customerservice.model.client.data.NewUser;
import com.microecom.customerservice.model.client.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class RestAuthClient implements AuthClient {
    private final String authRestUri;

    private final RestTemplate rest;

    public RestAuthClient(
            @Value("${customer-service.client.auth.uri}") String authRestUri,
            @Autowired RestTemplateBuilder restBuilder
    ) {
        this.authRestUri = authRestUri;
        this.rest = restBuilder.build();
    }

    @Override
    public User create(NewUser user) throws IllegalArgumentException {
        var created = rest.postForEntity(
                authRestUri + "/V1/user",
                new NewUserArgument(user.getLogin(), user.getPassword()),
                UserObject.class
        );
        if (created.getStatusCode() != HttpStatus.CREATED) {
            throw new IllegalArgumentException("Failed to create a user");
        }

        return new User(user.getLogin(), created.getBody().getId(), created.getBody().getCreated());
    }

    @Override
    public Optional<User> get(String id) {
        var response = rest.getForEntity(authRestUri + "/V1/user/" + id, UserObject.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            return Optional.empty();
        }

        if (response.getBody().getLogin().isPresent()) {
            return Optional.of(
                    new User(
                            response.getBody().getLogin().get(),
                            response.getBody().getId(),
                            response.getBody().getCreated()
                    )
            );
        }
        return Optional.of(new User(response.getBody().getId(), response.getBody().getCreated()));
    }
}
