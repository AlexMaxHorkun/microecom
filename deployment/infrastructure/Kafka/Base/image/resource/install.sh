#!/usr/bin/env bash

env TERM=linux
env DEBIAN_FRONTEND=noninteractive
apt update
apt install -yqf apt-utils
apt install -yqf tzdata
ln -fs /usr/share/zoneinfo/America/Chicago /etc/localtime &&\
echo "America/Chicago" > /etc/timezone &&\
dpkg-reconfigure --frontend noninteractive tzdata
##Installation
apt install -yqf sudo\
    curl\
    software-properties-common\
    openjdk-8-jdk\
    unzip

cd /etc/kafka
tar -xzf kafka_2.12-2.5.0.tgz
mv kafka_2.12-2.5.0 kafka
