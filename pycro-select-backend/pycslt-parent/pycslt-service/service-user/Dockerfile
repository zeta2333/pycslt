FROM centos7-jdk17
MAINTAINER Pycro
EXPOSE 6817
ADD target/service-user-1.0-SNAPSHOT.jar /service-user-1.0-SNAPSHOT.jar

WORKDIR /
ENTRYPOINT ["java" , "-jar" , "service-user-1.0-SNAPSHOT.jar"]