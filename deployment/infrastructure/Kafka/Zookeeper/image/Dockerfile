FROM base-kafka:latest
COPY resource/zookeeper.properties /etc/kafka/
ENTRYPOINT /etc/kafka/kafka/bin/zookeeper-server-start.sh /etc/kafka/zookeeper.properties
