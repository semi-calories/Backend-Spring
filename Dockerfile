FROM openjdk:17
LABEL maintainer="zeun"
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} semicalorie-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/semicalorie-0.0.1-SNAPSHOT.jar"]