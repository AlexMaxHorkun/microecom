#!/bin/bash

cd image &&\
cp "../../../../../../code/service/Order/Event/target/event-0.0.1-SNAPSHOT.jar" "resource/event-0.0.1-SNAPSHOT.jar" &&\
docker build -t order-event-service:local .
