#!/bin/bash

cd /etc/docker && docker save order-event-service > order-event-service.tar &&\
 /snap/bin/microk8s ctr image import order-event-service.tar &&\
  rm order-event-service.tar
