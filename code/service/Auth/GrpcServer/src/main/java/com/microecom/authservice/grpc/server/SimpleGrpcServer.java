package com.microecom.authservice.grpc.server;

import com.microecom.authservice.grpc.definition.UserManagerGrpc;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SimpleGrpcServer implements GrpcServer {
    private final Integer port;

    private final UserManagerGrpc.UserManagerImplBase userManager;

    public SimpleGrpcServer(
            @Value("${microecom.grpc.server.port}") Integer port,
            @Autowired UserManagerGrpc.UserManagerImplBase userManager
    ) {
        this.port = port;
        this.userManager = userManager;
    }

    @Override
    public void serve() throws Exception {
        var server = ServerBuilder.forPort(port)
                .addService(userManager)
                .build();

        server.start();
        server.awaitTermination();
    }
}
