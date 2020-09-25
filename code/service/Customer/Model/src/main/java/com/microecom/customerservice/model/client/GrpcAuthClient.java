package com.microecom.customerservice.model.client;

import com.microecom.authservice.grpc.definition.UserManagerGrpc;
import com.microecom.authservice.grpc.definition.Users;
import com.microecom.customerservice.model.client.data.NewUser;
import com.microecom.customerservice.model.client.data.User;
import com.microecom.customerservice.model.client.exception.InvalidUserDataException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class GrpcAuthClient implements AuthClient {
    private final String uri;

    private final Integer port;

    public GrpcAuthClient(
            @Value("${customer-service.client.auth.grpc.uri}") String uri,
            @Value("${customer-service.client.auth.grpc.port}") Integer port
    ) {
        this.uri = uri;
        this.port = port;
    }

    @Override
    public User create(NewUser user) throws InvalidUserDataException {
        var channel = openChannel();
        var userManagerService = UserManagerGrpc.newBlockingStub(channel);

        try {
            var result = userManagerService.createLocal(
                    Users.NewLocalUserArg.newBuilder().setLogin(user.getLogin()).setPassword(user.getPassword()).build()
            );

            if (result.getResultCase() == Users.UpdatedUserResult.ResultCase.UPDATED) {
                return new User(
                        result.getUpdated().getLocal().getLogin(),
                        result.getUpdated().getId(),
                        Instant.ofEpochSecond(result.getUpdated().getCreatedTimestamp())
                );
            } else {
                throw createUserDataException(result);
            }
        } finally {
            channel.shutdown();
        }
    }

    @Override
    public Optional<User> get(String id) {
        var channel = openChannel();
        var userManagerService = UserManagerGrpc.newBlockingStub(channel);

        try {
            var found = userManagerService.find(Users.IdArg.newBuilder().setId(id).build());
            if (found.hasUser()) {
                //User was found
                if (found.getUser().hasLocal()) {
                    //User with credentials found
                    return Optional.of(
                            new User(
                                    found.getUser().getLocal().getLogin(),
                                    found.getUser().getId(),
                                    Instant.ofEpochSecond(found.getUser().getCreatedTimestamp())
                            )
                    );
                } else {
                    //User without credentials
                    return Optional.of(
                            new User(
                                    found.getUser().getId(),
                                    Instant.ofEpochSecond(found.getUser().getCreatedTimestamp())
                            )
                    );
                }
            } else {
                //No user found.
                return Optional.empty();
            }
        } finally {
            channel.shutdown();
        }
    }

    @Override
    public void delete(String id) throws IllegalArgumentException {
        var channel = openChannel();
        var userManagerService = UserManagerGrpc.newBlockingStub(channel);

        try {
            userManagerService.delete(Users.IdArg.newBuilder().setId(id).build());
        } finally {
            channel.shutdown();
        }
    }

    /**
     * @return Opened GRPC channel to auth service.
     */
    private ManagedChannel openChannel() {
        return ManagedChannelBuilder.forAddress(uri, port).usePlaintext().build();
    }

    private InvalidUserDataException createUserDataException(Users.UpdatedUserResult result) {
        switch (result.getResultCase()) {
            case INPUTERROR:
                return new InvalidUserDataException(result.getInputError());
            case CONSTRAINTVIOLATIONS:
                var map = new HashMap<String, Set<String>>();
                for (Users.Violation violation : result.getConstraintViolations().getViolationsList()) {
                    map.put(violation.getField(), new HashSet<String>(violation.getMessagesList()));
                }
                return new InvalidUserDataException(map);
            default:
                throw new RuntimeException("Unknown UpdatedUserResult format");
        }
    }
}
