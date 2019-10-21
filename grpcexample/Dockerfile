FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
RUN mkdir -p /app
WORKDIR /app
COPY . /app/
VOLUME /tmp
EXPOSE 1020
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/target/grpc-example-1.0.0-SNAPSHOT.jar"]