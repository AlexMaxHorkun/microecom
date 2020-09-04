#!/bin/bash

cd /etc/docker && docker save catalog-db > catalog-db.tar &&\
 /snap/bin/microk8s ctr image import catalog-db.tar &&\
  rm catalog-db.tar
