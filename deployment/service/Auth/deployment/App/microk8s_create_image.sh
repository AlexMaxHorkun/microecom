#!/bin/bash

cd /etc/docker && docker save auth-service > auth-service.tar &&\
 /snap/bin/microk8s ctr image import auth-service.tar &&\
  rm auth-service.tar
