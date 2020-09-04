#!/bin/bash

cd image &&\
cp "../../../../../../code/service/Customer/Http/target/http-0.0.1-SNAPSHOT.jar" "resource/http-0.0.1-SNAPSHOT.jar" &&\
docker build -t customer-http-service:local .
