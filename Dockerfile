# USE THE FOLLOWING COMMAND TO CHANGE THE DEFAULT SERVER PORT (WHICH IS 8080) :
# docker run -e BOOKS_BACK_SERVER_PORT=9090 -p 8080:9090 books-back-service

# BUILD STAGE
FROM gradle:jdk17-alpine AS build

WORKDIR /app

COPY gradlew build.gradle ./
COPY gradle ./gradle

RUN chmod +x gradlew

RUN ["./gradlew", "dependencies", "--no-daemon"]

COPY src ./src

ARG BUILD_VERSION=0
ARG BUILD_DATE=0

RUN sed -i "s/%VERSION_PLACEHOLDER%/${BUILD_VERSION}/g" ./src/main/resources/meta.properties
RUN sed -i "s/%BUILD_DATE_PLACEHOLDER%/${BUILD_DATE}/g" ./src/main/resources/meta.properties
RUN ["./gradlew", "build", "-x", "test", "--no-daemon"]

# UNIT TESTS
FROM build AS unit-tests

CMD ["./gradlew", "unit-test", "--no-daemon"]

# RUNTIME STAGE
FROM eclipse-temurin:17-jre-alpine AS runtime

ENV BOOKS_BACK_SERVER_PORT=${BOOKS_BACK_SERVER_PORT:-8080}

WORKDIR /app

COPY --from=build --exclude=*-plain.jar /app/build/libs/*.jar ./app.jar

EXPOSE ${BOOKS_BACK_SERVER_PORT}

CMD ["java", "-jar", "./app.jar"]