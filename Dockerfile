FROM openjdk:8-jdk-alpine as build
COPY ${JAR_FILE} luizalabs-backend.jar
ENTRYPOINT ["java","-jar","/luizalabs-backend.jar"]