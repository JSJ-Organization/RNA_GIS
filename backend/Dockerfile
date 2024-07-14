FROM amazoncorretto:17-alpine-jdk AS builder

# Install necessary packages
RUN apk update && apk add --no-cache bash dos2unix

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
RUN dos2unix gradlew && chmod +x gradlew

# Change ownership of the working directory to spring user
RUN chown -R spring:spring /backend

# Switch to spring user
USER spring:spring

# Use bash to run gradlew
RUN /bin/bash ./gradlew bootJar

# Use openjdk for the final stage
FROM amazoncorretto:17-alpine-jdk

WORKDIR /backend

COPY --from=builder /backend/build/libs/*.jar app.jar

ENV TZ=Asia/Seoul

USER spring:spring

ENTRYPOINT ["java", "-jar", "app.jar"]
