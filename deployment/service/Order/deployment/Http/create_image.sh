#!/bin/bash

cd image &&\
cp "../../../../../../code/service/Order/Http/target/http-0.0.1-SNAPSHOT.jar" "resource/http-0.0.1-SNAPSHOT.jar" &&\
docker build -t order-http-service:local .
