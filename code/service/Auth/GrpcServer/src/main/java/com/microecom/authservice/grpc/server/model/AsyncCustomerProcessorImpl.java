package com.microecom.authservice.grpc.server.model;

import com.microecom.authservice.grpc.definition.AsyncCustomerProcessorGrpc;
import com.microecom.authservice.grpc.definition.Users;
import com.microecom.authservice.model.storage.UserRepository;
import com.microecom.authservice.model.storage.data.CustomerInfusedUser;
import com.microecom.authservice.model.storage.data.UserCustomerUpdate;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class AsyncCustomerProcessorImpl extends AsyncCustomerProcessorGrpc.AsyncCustomerProcessorImplBase {
    private final UserRepository repo;

    private final Logger logger;

    private final int batchSize = 100;

    public AsyncCustomerProcessorImpl(@Autowired UserRepository repo) {
        this.repo = repo;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public StreamObserver<Users.CustomerData> process(final StreamObserver<Users.ProcessedCustomerData> responseObserver) {
        var processingExecutor = Executors.newFixedThreadPool(5);

        return new StreamObserver<Users.CustomerData>() {
            private final Set<UserCustomerUpdate> received = new HashSet<>();

            @Override
            public void onNext(Users.CustomerData customerData) {
                received.add(new UserCustomerUpdate(
                        customerData.getUserId(), customerData.getEmail(), customerData.getFirstName(),
                        customerData.getLastName(), customerData.getCustomerId()
                ));
                if (received.size() >= batchSize) {
                    invokeProcessing();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                logger.error(String.format("[PerfExp] Failed to update a user with customer data [error: %s]",
                        throwable.getMessage()));
            }

            @Override
            public void onCompleted() {
                if (received.size() != 0) {
                    invokeProcessing();
                }
                logger.info("[PerfExp] Finished receiving customer info to process");

                try {
                    processingExecutor.shutdown();
                    processingExecutor.awaitTermination(10, TimeUnit.MINUTES);
                    logger.info("[PerfExp] Completed processing customer data");
                    responseObserver.onCompleted();
                } catch (Throwable ex) {
                    logger.error(String.format("[PerfExp] Failed to finish processing customer data [error: %s]", ex.getMessage()));
                    responseObserver.onError(ex);
                }
            }

            private synchronized void sendUpdated(Set<CustomerInfusedUser> userData) {
                for (CustomerInfusedUser user : userData) {
                    responseObserver.onNext(
                            Users.ProcessedCustomerData.newBuilder()
                                    .setCustomerId(user.getCustomerId())
                                    .setCreated(user.getCreated().getEpochSecond())
                                    .setEmail(user.getCustomerEmail())
                                    .setFirstName(user.getCustomerFirstname())
                                    .setLastName(user.getCustomerLastname())
                                    .setUserId(user.getUserId())
                                    .build()
                    );
                }
            }

            private synchronized void invokeProcessing() {
                var toProcess = received.toArray(new UserCustomerUpdate[0]);
                received.clear();
                processingExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        var updated = repo.infuse(toProcess);
                        logger.info(String.format("[PerfExp] Updated %d user records records", updated.size()));
                        sendUpdated(updated);
                    }
                });
            }
        };
    }
}
