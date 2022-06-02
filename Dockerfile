FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=/build/libs/restclient-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} restclient.jar
ENTRYPOINT ["java","-jar","restclient.jar"]