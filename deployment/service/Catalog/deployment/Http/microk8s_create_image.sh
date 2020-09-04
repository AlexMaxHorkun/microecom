#!/bin/bash

cd /etc/docker && docker save catalog-http-service > catalog-http-service.tar &&\
 /snap/bin/microk8s ctr image import catalog-http-service.tar &&\
  rm catalog-http-service.tar
