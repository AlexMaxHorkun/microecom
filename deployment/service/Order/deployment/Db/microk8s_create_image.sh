#!/bin/bash

cd /etc/docker && docker save order-db > order-db.tar &&\
 /snap/bin/microk8s ctr image import order-db.tar &&\
  rm order-db.tar
