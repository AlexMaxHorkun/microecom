#!/bin/bash

cd /etc/docker && docker save inventory-http-service > inventory-http-service.tar &&\
 /snap/bin/microk8s ctr image import inventory-http-service.tar &&\
  rm inventory-http-service.tar
