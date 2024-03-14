FROM openjdk:17-jdk-slim-buster
WORKDIR /app

COPY /build/libs/arty_bot-0.0.1-SNAPSHOT.jar build/

WORKDIR /app/build
EXPOSE 8181
ENTRYPOINT java -jar *.jar