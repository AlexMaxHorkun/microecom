#!/bin/bash

cd image &&\
cp "../../../../../../code/service/Catalog/Http/target/http-0.0.1-SNAPSHOT.jar" "resource/http-0.0.1-SNAPSHOT.jar" &&\
docker build -t catalog-http-service:local .
