package com.microecom.authservice.grpc.server.model;

import com.microecom.authservice.grpc.definition.UserManagerGrpc;
import com.microecom.authservice.grpc.definition.Users;
import com.microecom.authservice.grpc.server.model.data.LocalUserUpdate;
import com.microecom.authservice.grpc.server.model.data.NewLocalUser;
import com.microecom.authservice.model.UserManager;
import com.microecom.authservice.model.data.UserWithLogin;
import com.microecom.authservice.model.exception.InvalidUserDataException;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.ZonedDateTime;
import java.util.*;

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
    public void createLocal(Users.NewLocalUserArg request, StreamObserver<Users.UpdatedUserResult> responseObserver) {
        logger.info("GRPC.createLocal() requested");
        var newUser = new NewLocalUser(request.getLogin(), request.getPassword(), ZonedDateTime.now());
        var violationsFound = validate(newUser);
        if (violationsFound.isEmpty()) {
            try {
                //Creating new user
                var user = userManager.create(newUser);
                var result = Users.UpdatedUserResult.newBuilder()
                        .setUpdated(
                                Users.User.newBuilder()
                                        .setId(user.getId())
                                        .setCreatedTimestamp(user.getCreated().toInstant().getEpochSecond())
                                        .setLocal(
                                                Users.LocalUserData.newBuilder()
                                                        .setLogin(((UserWithLogin) user).getLogin())
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();
                responseObserver.onNext(result);
            } catch (InvalidUserDataException ex) {
                //Invalid input
                logger.warn("GRPC.createLocal() Invalid: " + ex.getMessage());
                responseObserver.onNext(Users.UpdatedUserResult.newBuilder().setInputError(ex.getMessage()).build());
            }
        } else {
            //Bean is invalid
            logger.warn("GRPC.createLocal() Invalid bean: " + request.getLogin());
            responseObserver.onNext(
                    Users.UpdatedUserResult.newBuilder().setConstraintViolations(violationsFound.get()).build()
            );
        }

        responseObserver.onCompleted();
    }

    @Override
    public void find(Users.IdArg request, StreamObserver<Users.FoundUserResult> responseObserver) {
        logger.info("GRPC.find() requested with " + request.getId());
        var found = userManager.findById(request.getId());
        if (found.isPresent()) {
            var userBuilder = Users.User.newBuilder()
                    .setId(found.get().getId())
                    .setCreatedTimestamp(found.get().getCreated().toInstant().getEpochSecond());
            if (found.get() instanceof UserWithLogin) {
                userBuilder.setLocal(
                        Users.LocalUserData.newBuilder()
                                .setLogin(((UserWithLogin) found.get()).getLogin())
                                .build()
                );
            }
            responseObserver.onNext(Users.FoundUserResult.newBuilder().setUser(userBuilder.build()).build());
        } else {
            logger.info("GRPC.find(" + request.getId() + ") Not Found!");
            responseObserver.onNext(Users.FoundUserResult.newBuilder().build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void updateLocal(
            Users.LocalUserUpdateArg request,
            StreamObserver<Users.UpdatedUserResult> responseObserver
    ) {
        logger.info("GRPC.updateLocal() requested for " + request.getId());
        var update = new LocalUserUpdate(request.getId(), request.getPassword());
        var violations = validate(update);
        if (violations.isEmpty()) {
            try {
                var updated = userManager.update(update);
                responseObserver.onNext(
                        Users.UpdatedUserResult.newBuilder()
                                .setUpdated(
                                        Users.User.newBuilder()
                                                .setId(updated.getId())
                                                .setCreatedTimestamp(updated.getCreated().toInstant().getEpochSecond())
                                                .setLocal(
                                                        Users.LocalUserData.newBuilder()
                                                                .setLogin(((UserWithLogin) updated).getLogin())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                );
            } catch (InvalidUserDataException ex) {
                logger.warn("GRPC.updateLocal(" + request.getId() + ") Update Failed: " + ex.getMessage());
                responseObserver.onNext(Users.UpdatedUserResult.newBuilder().setInputError(ex.getMessage()).build());
            }
        } else {
            logger.warn("GRPC.updateLocal(" + request.getId() + ") Update Bean is Invalid");
            responseObserver.onNext(
                    Users.UpdatedUserResult.newBuilder().setConstraintViolations(violations.get()).build()
            );
        }

        responseObserver.onCompleted();
    }

    @Override
    public void delete(Users.IdArg request, StreamObserver<Users.DeletedResult> responseObserver) {
        logger.info("GRPC.delete() requested for " + request.getId());
        userManager.delete(request.getId());
        responseObserver.onNext(Users.DeletedResult.newBuilder().build());
        responseObserver.onCompleted();
    }

    /**
     * Validate a bean.
     *
     * @param input Object to validate.
     * @return Violations if any.
     */
    private Optional<Users.ConstraintViolations> validate(Object input) {
        var violations = validator.validate(input);
        if (violations.isEmpty()) {
            return Optional.empty();
        }

        var violationsMap = new HashMap<String, Set<String>>();
        for (ConstraintViolation<Object> violation : violations) {
            if (!violationsMap.containsKey(violation.getPropertyPath().toString())) {
                violationsMap.put(violation.getPropertyPath().toString(), new HashSet<String>());
            }
            violationsMap.get(violation.getPropertyPath().toString()).add(violation.getMessage());
        }

        return Optional.of(createViolations(violationsMap));
    }

    private Users.ConstraintViolations createViolations(Map<String, Set<String>> violationsMap) {
        var grpcViolations = Users.ConstraintViolations.newBuilder();
        var i = 0;
        for (Map.Entry<String, Set<String>> entry : violationsMap.entrySet()) {
            var grpcViolation = Users.Violation.newBuilder().setField(entry.getKey());
            var mi = 0;
            for (String message : entry.getValue()) {
                grpcViolation.setMessages(mi++, message);
            }
            grpcViolations.setViolations(i++, grpcViolation.build());
        }

        return grpcViolations.build();
    }

    private Users.ConstraintViolations createViolations(String field, String message) {
        var violationsMap = new HashMap<String, Set<String>>();
        violationsMap.put(field, new HashSet<String>()).add(message);

        return createViolations(violationsMap);
    }
}
