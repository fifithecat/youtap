FROM maven:3.6.3-jdk-8-slim AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/

RUN mvn clean install

WORKDIR /build/target/


EXPOSE 8080


cmd ["java", "-jar", "youtap-interview-0.0.1-SNAPSHOT.jar"]
