#!/bin/bash

cd image &&\
cp "../../../../../../code/service/Catalog/Event/target/event-0.0.1-SNAPSHOT.jar" "resource/event-0.0.1-SNAPSHOT.jar" &&\
docker build -t catalog-event-service:local .
