#!/bin/bash

cd image &&\
cp "../../../../../../code/service/Payment/Http/target/http-0.0.1-SNAPSHOT.jar" "resource/http-0.0.1-SNAPSHOT.jar" &&\
docker build -t payment-http-service:local .
