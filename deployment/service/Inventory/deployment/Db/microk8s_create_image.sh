#!/bin/bash

cd /etc/docker && docker save inventory-db > inventory-db.tar &&\
 /snap/bin/microk8s ctr image import inventory-db.tar &&\
  rm inventory-db.tar
