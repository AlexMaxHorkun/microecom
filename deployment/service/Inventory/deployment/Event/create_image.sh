#!/bin/bash

cd image &&\
cp "../../../../../../code/service/Inventory/Event/target/event-0.0.1-SNAPSHOT.jar" "resource/event-0.0.1-SNAPSHOT.jar" &&\
docker build -t inventory-event-service:local .
