#!/bin/bash

cd /etc/docker && docker save auth-db > auth-db.tar &&\
 /snap/bin/microk8s ctr image import auth-db.tar &&\
  rm auth-db.tar
