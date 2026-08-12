# USE THE FOLLOWING COMMAND TO CHANGE THE DEFAULT SERVER PORT (WHICH IS 8080) :
# docker run -e BOOKS_BACK_SERVER_PORT=9090 -p 8080:9090 books-back-service

# BASE STAGE
FROM gradle:jdk17-alpine AS base

WORKDIR /app

COPY gradlew build.gradle ./
COPY gradle ./gradle

RUN chmod +x gradlew

RUN ["./gradlew", "dependencies", "--no-daemon"]

COPY src ./src

RUN ["./gradlew", "compileJava", "processResources", "compileTestJava", "processTestResources", "--no-daemon"]

# UNIT TESTS
FROM base AS unit-tests

RUN ["./gradlew", "unit-test", "--no-daemon"]

# SONARQUBE TESTS
FROM base AS sonarqube-tests

RUN ["./gradlew", "sonar", "--no-daemon"]

# SPOTBUGS TESTS
FROM base AS spotbugs-tests

RUN ["./gradlew", "spotbugsMain", "--no-daemon"]

# BUILD STAGE
FROM base AS build

WORKDIR /app

ARG BUILD_VERSION=0
ARG BUILD_DATE=0

RUN sed -i "s/%VERSION_PLACEHOLDER%/${BUILD_VERSION}/g" ./src/main/resources/meta.properties
RUN sed -i "s/%BUILD_DATE_PLACEHOLDER%/${BUILD_DATE}/g" ./src/main/resources/meta.properties
RUN ["./gradlew", "build", "-x", "test", "-x", "check", "--no-daemon"]

# RUNTIME STAGE
FROM eclipse-temurin:17-jre-alpine AS runtime

ENV BOOKS_BACK_SERVER_PORT=${BOOKS_BACK_SERVER_PORT:-8080}

WORKDIR /app

COPY --from=build --exclude=*-plain.jar /app/build/libs/*.jar ./app.jar

EXPOSE ${BOOKS_BACK_SERVER_PORT}

CMD ["java", "-jar", "./app.jar"]