#!/bin/bash

kubectl delete -f k8/service-config.yaml --cascade=true --grace-period=1
