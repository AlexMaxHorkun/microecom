#!/bin/bash

cd ../Base && bash create_image.sh

cd ../Broker/image &&
docker build -t kafka-broker:local .
