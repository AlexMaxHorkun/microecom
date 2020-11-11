package com.microecom.authservice.grpc.server;

import com.microecom.authservice.grpc.definition.AsyncCustomerProcessorGrpc;
import com.microecom.authservice.grpc.definition.UserManagerGrpc;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SimpleGrpcServer implements GrpcServer {
    private final Integer port;

    private final UserManagerGrpc.UserManagerImplBase userManager;

    private final AsyncCustomerProcessorGrpc.AsyncCustomerProcessorImplBase processor;

    public SimpleGrpcServer(
            @Value("${microecom.grpc.server.port}") Integer port,
            @Autowired UserManagerGrpc.UserManagerImplBase userManager,
            @Autowired AsyncCustomerProcessorGrpc.AsyncCustomerProcessorImplBase processor
    ) {
        this.port = port;
        this.userManager = userManager;
        this.processor = processor;
    }

    @Override
    public void serve() throws Exception {
        var server = ServerBuilder.forPort(port)
                .addService(userManager)
                .addService(processor)
                .build();

        server.start();
        server.awaitTermination();
    }
}
