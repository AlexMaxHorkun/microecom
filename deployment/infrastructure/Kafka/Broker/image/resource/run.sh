#!/usr/bin/env bash

cd /etc/kafka
rando=$(od -A n -t d -N 1 /dev/urandom)
rando=$(echo $rando)
sed -i "s/broker.id=0/broker.id=$rando/g" server.properties
kafka/bin/kafka-server-start.sh server.properties
