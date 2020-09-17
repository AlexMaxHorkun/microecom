#!/bin/bash

cd image &&\
cp "../../../../../../code/service/Auth/GrpcServer/target/grpc-server-0.0.1-SNAPSHOT.jar" "resource/grpc-server-0.0.1-SNAPSHOT.jar" &&\
docker build -t auth-grpc-service:local .
