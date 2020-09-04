#!/bin/bash

cd /etc/docker && docker save payment-db > payment-db.tar &&\
 /snap/bin/microk8s ctr image import payment-db.tar &&\
  rm payment-db.tar
