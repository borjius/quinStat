FROM java:8-jdk-alpine

LABEL maintainer="pilit63@gmail.com"

ENV project.version 1.0

RUN apk add --no-cache bash \
    --update curl

USER root

COPY ./quinStat-ingestor/target/*.jar /opt/quinStatServer.jar
COPY ./db/quinData.mv.db /opt/db/quinData.mv.db

WORKDIR /opt/

RUN chown -R root .; chmod -R 755 .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/quinStatServer.jar"]
