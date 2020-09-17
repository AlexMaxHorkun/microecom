#!/bin/bash

cd /etc/docker && docker save auth-http-service > auth-http-service.tar &&\
 /snap/bin/microk8s ctr image import auth-http-service.tar &&\
  rm auth-http-service.tar
