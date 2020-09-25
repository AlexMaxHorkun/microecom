package com.microecom.authservice.grpc.server.model;

import com.microecom.authservice.grpc.definition.UserManagerGrpc;
import com.microecom.authservice.grpc.definition.Users;
import com.microecom.authservice.grpc.server.model.data.LocalUserUpdate;
import com.microecom.authservice.grpc.server.model.data.NewLocalUser;
import com.microecom.authservice.model.UserManager;
import com.microecom.authservice.model.data.UserWithCredentials;
import com.microecom.authservice.model.exception.InvalidUserDataException;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.ZonedDateTime;

@Service
public class UserManagerImpl extends UserManagerGrpc.UserManagerImplBase {
    private final Validator validator;

    private final UserManager userManager;

    private final Logger logger;

    public UserManagerImpl(@Autowired UserManager userManager, @Autowired Validator validator) {
        this.userManager = userManager;
        this.validator = validator;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public void createLocal(Users.NewLocalUser request, StreamObserver<Users.User> responseObserver) {
        logger.info("GRPC.createLocal() requested");
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
                logger.warn("GRPC.createLocal() Failed: " + ex.getMessage());
                responseObserver.onError(ex);
            }
        }
    }

    @Override
    public void find(Users.SearchById request, StreamObserver<Users.User> responseObserver) {
        logger.info("GRPC.find() requested with " + request.getId());
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
            logger.info("GRPC.find(" + request.getId() + ") Not Found!");
            responseObserver.onError(new IllegalArgumentException("User with given ID was not found"));
        }
    }

    @Override
    public void updateLocal(Users.LocalUserUpdate request, StreamObserver<Users.User> responseObserver) {
        logger.info("GRPC.updateLocal() requested for " + request.getId());
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
                logger.warn("GRPC.updateLocal(" + request.getId() + ") Update Failed: " + ex.getMessage());
                responseObserver.onError(ex);
            }
        }
    }

    @Override
    public void delete(Users.SearchById request, StreamObserver<Users.DeletedResult> responseObserver) {
        logger.info("GRPC.delete() requested for " + request.getId());
        userManager.delete(request.getId());
        responseObserver.onNext(Users.DeletedResult.newBuilder().build());
        responseObserver.onCompleted();
    }

    private boolean isValid(Object input, StreamObserver response) {
        var violations = validator.validate(input);
        if (!violations.isEmpty()) {
            var exception = new ConstraintViolationException(violations);
            logger.warn("GRPC.isValid() Failed: " + exception.getMessage());
            response.onError(exception);

            return false;
        }

        return true;
    }
}
