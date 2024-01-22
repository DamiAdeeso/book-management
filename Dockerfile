FROM openjdk:21
MAINTAINER damiadeeso.com
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

