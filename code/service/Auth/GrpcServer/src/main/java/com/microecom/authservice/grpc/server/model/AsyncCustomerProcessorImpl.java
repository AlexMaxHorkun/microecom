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

@Service
public class AsyncCustomerProcessorImpl extends AsyncCustomerProcessorGrpc.AsyncCustomerProcessorImplBase {
    private final UserRepository repo;

    private final Logger logger;

    private final int batchSize = 500;

    public AsyncCustomerProcessorImpl(@Autowired UserRepository repo) {
        this.repo = repo;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public StreamObserver<Users.CustomerData> process(final StreamObserver<Users.ProcessedCustomerData> responseObserver) {
        return new StreamObserver<Users.CustomerData>() {
            private Set<UserCustomerUpdate> received = new HashSet<>();

            @Override
            public void onNext(Users.CustomerData customerData) {
                received.add(new UserCustomerUpdate(
                        customerData.getUserId(), customerData.getEmail(), customerData.getFirstName(),
                        customerData.getLastName(), customerData.getCustomerId()
                ));
                if (received.size() >= batchSize) {
                    updateUsers();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                logger.error("[PerfExp] Failed to update a user with customer data");
            }

            @Override
            public void onCompleted() {
                if (received.size() != 0) {
                    updateUsers();
                }

                logger.info("[PerfExp] Finished processing customer info");
                responseObserver.onCompleted();
            }

            private void sendUpdated(CustomerInfusedUser user) {
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

            private void updateUsers() {
                var updated = repo.infuse(received);
                logger.info(String.format("[PerfExp] Updated %d user records records", updated.size()));
                received.clear();
                updated.forEach(this::sendUpdated);
            }
        };
    }
}
