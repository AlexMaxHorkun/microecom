#!/bin/bash

cd /etc/docker && docker save auth-grpc-service > auth-grpc-service.tar &&\
 /snap/bin/microk8s ctr image import auth-grpc-service.tar &&\
  rm auth-grpc-service.tar
