#!/bin/bash

cd image &&\
cp "../../../../../../code/service/Auth/Http/target/http-0.0.1-SNAPSHOT.jar" "resource/http-0.0.1-SNAPSHOT.jar" &&\
docker build -t auth-http-service:local .
