#!/bin/bash

cd ../Base && bash create_image.sh

cd ../Zookeeper/image &&
docker build -t kafka-zookeeper:local .
