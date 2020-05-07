#!/usr/bin/env bash

cd /etc/kafka
sed -i "/s/broker.id=0/broker.id=$(1 + RANDOM % 1000)/g" server.properties
kafka/bin/kafka-server-start.sh server.properties
