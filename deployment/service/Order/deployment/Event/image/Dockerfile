FROM openjdk:14
COPY resource/event-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5013", "-jar","/app.jar"]
