#!/bin/bash

cd /etc/docker && docker save order-http-service > order-http-service.tar &&\
 /snap/bin/microk8s ctr image import order-http-service.tar &&\
  rm order-http-service.tar
