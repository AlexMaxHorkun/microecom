#!/bin/bash

cd /etc/docker && docker save inventory-event-service > inventory-event-service.tar &&\
 /snap/bin/microk8s ctr image import inventory-event-service.tar &&\
  rm inventory-event-service.tar
