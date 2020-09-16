package com.microecom.authservice.grpc.server.model;

import com.microecom.authservice.grpc.definition.UserManagerGrpc;
import com.microecom.authservice.grpc.definition.Users;
import com.microecom.authservice.grpc.server.model.data.NewLocalUser;
import com.microecom.authservice.model.UserManager;
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
    public void createLocal(Users.NewLocalUser request, StreamObserver<Users.LocalUser> responseObserver) {
        var newUser = new NewLocalUser(request.getLogin(), request.getPassword(), ZonedDateTime.now());
        var violations = validator.validate(newUser);
        if (!violations.isEmpty()) {
            responseObserver.onError(new ConstraintViolationException(violations));
            responseObserver.onCompleted();
        } else {
            try {
                var user = userManager.create(newUser);
                var result = Users.LocalUser.newBuilder()
                        .setLogin(request.getLogin())
                        .setUserData(
                                Users.User.newBuilder()
                                        .setId(user.getId())
                                        .setCreatedTimestamp(user.getCreated().toInstant().getEpochSecond())
                                        .build()
                        ).build();
                responseObserver.onNext(result);
                responseObserver.onCompleted();
            } catch (Throwable ex) {
                responseObserver.onError(ex);
            }
        }
    }

    @Override
    public void find(Users.SearchById request, StreamObserver<Users.User> responseObserver) {
        super.find(request, responseObserver);
    }

    @Override
    public void updateLocal(Users.LocalUserUpdate request, StreamObserver<Users.User> responseObserver) {
        super.updateLocal(request, responseObserver);
    }

    @Override
    public void delete(Users.SearchById request, StreamObserver<Users.DeletedResult> responseObserver) {
        super.delete(request, responseObserver);
    }
}
