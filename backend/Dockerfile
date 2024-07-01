FROM amazoncorretto:17-alpine-jdk AS builder

# Install bash
RUN apk update && apk add --no-cache bash

# Create user and group
RUN addgroup -S spring && adduser -S spring -G spring

# Set work directory
WORKDIR /backend

# Copy necessary files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Ensure the gradlew file is in Unix format and has execute permission
RUN dos2unix gradlew
RUN chmod +x gradlew

# Change ownership of the working directory to spring user
RUN chown -R spring:spring /backend

# Switch to spring user
USER spring:spring

# Use bash to run gradlew
RUN /bin/bash ./gradlew bootJar

# Use openjdk for the final stage
FROM openjdk:17
WORKDIR /backend
COPY --from=builder /backend/build/libs/*.jar app.jar

ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-jar", "app.jar"]
VOLUME /tmp
