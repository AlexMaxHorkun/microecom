### Istio notes
Use `kubectl label namespace default istio-injection=disabled --overwrite=true`
before creating Kafka services, use `kubectl label namespace default istio-injection=enabled --overwrite=true`
after for every other service.
