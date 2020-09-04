#!/bin/bash

kubectl delete -f k8/service.yaml --cascade=true --grace-period=1
kubectl delete -f k8/deployment.yaml --cascade=true --grace-period=1
