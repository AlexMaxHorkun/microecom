#!/bin/bash

cd /etc/docker && docker save payment-http-service > payment-http-service.tar &&\
 /snap/bin/microk8s ctr image import payment-http-service.tar &&\
  rm payment-http-service.tar
