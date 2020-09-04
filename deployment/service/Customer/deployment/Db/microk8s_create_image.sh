#!/bin/bash

cd /etc/docker && docker save customer-db > customer-db.tar &&\
 /snap/bin/microk8s ctr image import customer-db.tar &&\
  rm customer-db.tar
