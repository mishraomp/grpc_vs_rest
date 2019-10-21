FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
RUN mkdir -p /app2
WORKDIR /app2
COPY . /app2/
VOLUME /tmp
EXPOSE 8090
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app2/target/spring-reactive-mongo-db-0.0.1-SNAPSHOT.jar"]