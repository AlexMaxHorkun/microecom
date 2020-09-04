#!/bin/bash

cd /etc/docker && docker save catalog-event-service > catalog-event-service.tar &&\
 /snap/bin/microk8s ctr image import catalog-event-service.tar &&\
  rm catalog-event-service.tar
