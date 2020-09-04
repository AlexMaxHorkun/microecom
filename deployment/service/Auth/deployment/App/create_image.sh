#!/bin/bash

cd image &&\
cp "../../../../../../code/service/Auth/target/auth-service-0.0.1-SNAPSHOT.jar" "resource/auth-service-0.0.1-SNAPSHOT.jar" &&\
docker build -t auth-service:local .
