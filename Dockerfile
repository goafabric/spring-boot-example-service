#FROM openjdk:8-jdk-alpine
FROM adoptopenjdk/openjdk11:alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Xms128m","-Xmx256m","-jar","/app.jar"]