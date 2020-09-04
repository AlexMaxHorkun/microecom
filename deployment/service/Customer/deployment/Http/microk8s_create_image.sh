#!/bin/bash

cd /etc/docker && docker save customer-http-service > customer-http-service.tar &&\
 /snap/bin/microk8s ctr image import customer-http-service.tar &&\
  rm customer-http-service.tar
