docker container run base-kafka:latest "/etc/kafka/kafka/bin/kafka-topics.sh" "--create" "--bootstrap-server=docker.host.internal:9092" "--replication-factor=5" "--partitions=1" "--topic=%1"