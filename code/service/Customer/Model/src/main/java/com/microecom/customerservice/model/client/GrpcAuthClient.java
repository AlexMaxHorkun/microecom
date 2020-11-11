package com.microecom.customerservice.model.client;

import com.microecom.authservice.grpc.definition.AsyncCustomerProcessorGrpc;
import com.microecom.authservice.grpc.definition.UserManagerGrpc;
import com.microecom.authservice.grpc.definition.Users;
import com.microecom.customerservice.model.client.data.NewUser;
import com.microecom.customerservice.model.client.data.User;
import com.microecom.customerservice.model.client.exception.InvalidUserDataException;
import com.microecom.customerservice.model.storage.CustomerRepository;
import com.microecom.customerservice.model.storage.data.CustomerToProcess;
import com.microecom.customerservice.model.storage.data.UserDataUpdate;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class GrpcAuthClient implements AuthClient {
    private final String uri;

    private final Integer port;

    private final CustomerRepository repo;

    private final Logger logger;

    private final int[] batchSizes = {100, 1000, 5000, 10000};

    public GrpcAuthClient(
            @Value("${customer-service.client.auth.grpc.uri}") String uri,
            @Value("${customer-service.client.auth.grpc.port}") Integer port,
            @Autowired CustomerRepository repo
    ) {
        this.uri = uri;
        this.port = port;
        this.repo = repo;
        this.logger = LoggerFactory.getLogger(getClass());
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

    @Override
    public long processCustomers() {
        var total = repo.count();
        if (total == 0) {
            return 0;
        }
        final var processedCount = new AtomicInteger();
        int batchSize = calculateBatchSize(total);
        var postProcessorExecutor = Executors.newFixedThreadPool(5);
        var responseLatch = new CountDownLatch(1);
        var channel = openChannel();
        var service = AsyncCustomerProcessorGrpc.newStub(channel);
        try {
            var responseObserver = new StreamObserver<Users.ProcessedCustomerData>() {
                private final Set<UserDataUpdate> received = new HashSet<>();

                @Override
                public void onNext(Users.ProcessedCustomerData processedCustomerData) {
                    received.add(new UserDataUpdate(processedCustomerData.getCustomerId(),
                            Instant.ofEpochSecond(processedCustomerData.getCreated())));
                    if (received.size() >= batchSize) {
                        doUpdates();
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    logger.error("[PerfExp] Failed to processed user service processed customer");
                    logger.error(throwable.getLocalizedMessage());
                    waitOnPostProcessing();
                }

                @Override
                public void onCompleted() {
                    if (received.size() > 0) {
                        doUpdates();
                    }
                    logger.info("[PerfExp] Retrieved all user infused customers");
                    waitOnPostProcessing();
                }

                private synchronized void doUpdates() {
                    var toProcess = received.toArray(new UserDataUpdate[0]);
                    received.clear();
                    postProcessorExecutor.submit(new Runnable() {
                        @Override
                        public void run() {
                            repo.updateWithUserData(toProcess);
                            processedCount.addAndGet(toProcess.length);
                            logger.info(String.format("[PerfExp] Processed %d customers", toProcess.length));
                        }
                    });
                }

                private void waitOnPostProcessing() {
                    try {
                        postProcessorExecutor.shutdown();
                        postProcessorExecutor.awaitTermination(10, TimeUnit.MINUTES);
                    } catch (Throwable ex) {
                        logger.error("[PerfExp] Failed to post-processed user service processed customer");
                        throw new RuntimeException("Failed to post-process", ex);
                    } finally {
                        responseLatch.countDown();
                    }
                }
            };

            var requestObserver = service.process(responseObserver);

            try {
                var pages = (int) Math.ceil((double) total / (double) batchSize);
                for (int page = 0; page < pages; page++) {
                    repo.readBatch(batchSize, page).map(GrpcAuthClient::convert).forEach(requestObserver::onNext);
                }

                requestObserver.onCompleted();
                logger.info(String.format("[PerfExp] Sent all %d customers data", total));
            } catch (Throwable ex) {
                logger.error(String.format("[PerfExp] Failed to send customer data over [error: %s]", ex.getMessage()));
                requestObserver.onError(ex);
            }

            try {
                postProcessorExecutor.awaitTermination(10, TimeUnit.MINUTES);
            } catch (Throwable ex) {
                logger.error(String.format("[PerfExp] Failed to finish post processing [error: %s]", ex.getMessage()));
            }
        } finally {
            channel.shutdown();
        }
        try {
            responseLatch.await();
        } catch (Throwable ex) {
            logger.error("[PerfExp] Failed to wait for post processing");
        }

        return processedCount.get();
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

    private int calculateBatchSize(long count) {
        int size = batchSizes[0];
        for (int batchSize : batchSizes) {
            if (Math.floorDiv(count, batchSize) < 3) {
                break;
            }
            size = batchSize;
        }

        return size;
    }

    private final static Users.CustomerData convert(CustomerToProcess p) {
        return Users.CustomerData.newBuilder()
                .setCustomerId(p.getCustomerId())
                .setEmail(p.getEmail())
                .setFirstName(p.getFirstname())
                .setLastName(p.getLastname())
                .setUserId(p.getUserId())
                .build();
    }
}
