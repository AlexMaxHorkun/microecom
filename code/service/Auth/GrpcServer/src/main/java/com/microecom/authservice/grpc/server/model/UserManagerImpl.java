package com.microecom.authservice.grpc.server.model;

import com.microecom.authservice.grpc.definition.UserManagerGrpc;
import com.microecom.authservice.grpc.definition.Users;
import com.microecom.authservice.grpc.server.model.data.LocalUserUpdate;
import com.microecom.authservice.grpc.server.model.data.NewLocalUser;
import com.microecom.authservice.model.UserManager;
import com.microecom.authservice.model.data.UserWithCredentials;
import com.microecom.authservice.model.exception.InvalidUserDataException;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.ZonedDateTime;

@Service
public class UserManagerImpl extends UserManagerGrpc.UserManagerImplBase {
    private final Validator validator;

    private final UserManager userManager;

    public UserManagerImpl(@Autowired UserManager userManager, @Autowired Validator validator) {
        this.userManager = userManager;
        this.validator = validator;
    }

    @Override
    public void createLocal(Users.NewLocalUser request, StreamObserver<Users.User> responseObserver) {
        var newUser = new NewLocalUser(request.getLogin(), request.getPassword(), ZonedDateTime.now());
        if (isValid(newUser, responseObserver)) {
            try {
                var user = userManager.create(newUser);
                var result = Users.User.newBuilder()
                        .setId(user.getId())
                        .setCreatedTimestamp(user.getCreated().toInstant().getEpochSecond())
                        .setLocal(Users.LocalUserData.newBuilder().setLogin(request.getLogin()).build())
                        .build();
                responseObserver.onNext(result);
                responseObserver.onCompleted();
            } catch (InvalidUserDataException ex) {
                responseObserver.onError(ex);
            }
        }
    }

    @Override
    public void find(Users.SearchById request, StreamObserver<Users.User> responseObserver) {
        var found = userManager.findById(request.getId());
        if (found.isPresent()) {
            var userBuilder = Users.User.newBuilder()
                    .setId(found.get().getId())
                    .setCreatedTimestamp(found.get().getCreated().toInstant().getEpochSecond());
            if (found.get() instanceof UserWithCredentials) {
                userBuilder.setLocal(
                        Users.LocalUserData.newBuilder()
                                .setLogin(((UserWithCredentials) found.get()).getLogin())
                                .build()
                );
            }
            responseObserver.onNext(userBuilder.build());
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new IllegalArgumentException("User with given ID was not found"));
        }
    }

    @Override
    public void updateLocal(Users.LocalUserUpdate request, StreamObserver<Users.User> responseObserver) {
        var update = new LocalUserUpdate(request.getId(), request.getPassword());
        if (isValid(update, responseObserver)) {
            try {
                var updated = userManager.update(update);
                responseObserver.onNext(
                        Users.User.newBuilder()
                                .setId(updated.getId())
                                .setCreatedTimestamp(updated.getCreated().toInstant().getEpochSecond())
                                .setLocal(
                                        Users.LocalUserData.newBuilder()
                                                .setLogin(((UserWithCredentials) updated).getLogin())
                                                .build()
                                )
                                .build()
                );
                responseObserver.onCompleted();
            } catch (InvalidUserDataException ex) {
                responseObserver.onError(ex);
            }
        }
    }

    @Override
    public void delete(Users.SearchById request, StreamObserver<Users.DeletedResult> responseObserver) {
        userManager.delete(request.getId());
        responseObserver.onNext(Users.DeletedResult.newBuilder().build());
        responseObserver.onCompleted();
    }

    private boolean isValid(Object input, StreamObserver response) {
        var violations = validator.validate(input);
        if (!violations.isEmpty()) {
            response.onError(new ConstraintViolationException(violations));
            response.onCompleted();

            return false;
        }

        return true;
    }
}
