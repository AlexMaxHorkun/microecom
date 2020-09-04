#!/bin/bash

cd image &&\
cp "../../../../../../code/service/Inventory/Http/target/http-0.0.1-SNAPSHOT.jar" "resource/http-0.0.1-SNAPSHOT.jar" &&\
docker build -t inventory-http-service:local .
