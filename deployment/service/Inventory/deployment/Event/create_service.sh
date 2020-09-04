#!/bin/bash

kubectl label namespace default istio-injection=disabled --overwrite=true &&\
kubectl create -f k8/deployment.yaml &&\
kubectl create -f k8/service.yaml
