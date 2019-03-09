#FROM openjdk:8-jdk-alpine
FROM adoptopenjdk/openjdk11:alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Xms128m","-Xmx256m","-Dcom.sun.management.jmxremote.port=50701","-Dcom.sun.management.jmxremote.rmi.port=50701","-Djava.rmi.server.hostname=0.0.0.0","-Dcom.sun.management.jmxremote.authenticate=false","-Dcom.sun.management.jmxremote.ssl=false","-jar","/app.jar"]

