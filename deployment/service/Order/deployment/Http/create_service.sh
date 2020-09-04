#!/bin/bash

kubectl label namespace default istio-injection=enabled --overwrite=true &&\
kubectl create -f k8/service-config.yaml
kubectl label namespace default istio-injection=disabled --overwrite=true
