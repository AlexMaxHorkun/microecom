package com.microecom.customerservice.model.client;

import com.microecom.authservice.grpc.definition.UserManagerGrpc;
import com.microecom.authservice.grpc.definition.Users;
import com.microecom.customerservice.model.client.data.NewUser;
import com.microecom.customerservice.model.client.data.User;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

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
    public User create(NewUser user) throws IllegalArgumentException {
        var channel = openChannel();
        var userManagerService = UserManagerGrpc.newBlockingStub(channel);

        try {
            var created = userManagerService.createLocal(
                    Users.NewLocalUser.newBuilder()
                            .setLogin(user.getLogin())
                            .setPassword(user.getPassword())
                            .build()
            );

            return new User(
                    created.getLocal().getLogin(),
                    created.getId(),
                    Instant.ofEpochSecond(created.getCreatedTimestamp())
            );
        } catch (StatusRuntimeException ex) {
            if (ex.getStatus().getCode() == Status.Code.UNKNOWN) {
                throw new IllegalArgumentException(ex.getMessage());
            } else {
                throw ex;
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
            var found = userManagerService.find(Users.SearchById.newBuilder().setId(id).build());
            String login;
            if (found.getAuthDataCase().equals(Users.User.AuthDataCase.LOCAL)) {
                return Optional.of(
                        new User(
                                found.getLocal().getLogin(),
                                found.getId(),
                                Instant.ofEpochSecond(found.getCreatedTimestamp())
                        )
                );
            } else {
                return Optional.of(new User(found.getId(), Instant.ofEpochSecond(found.getCreatedTimestamp())));
            }
        } catch (StatusRuntimeException ex) {
            if (ex.getStatus().getCode() == Status.Code.UNKNOWN) {
                return Optional.empty();
            } else {
                throw ex;
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
            userManagerService.delete(Users.SearchById.newBuilder().setId(id).build());
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
}
