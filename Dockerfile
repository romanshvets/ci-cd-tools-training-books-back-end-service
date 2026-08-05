# USE THE FOLLOWING COMMAND TO CHANGE THE DEFAULT SERVER PORT (WHICH IS 8080) :
# docker run -e BOOKS_BACK_SERVER_PORT=9090 -p 8080:9090 books-back-service

# BUILD STAGE
FROM gradle:jdk17-alpine AS books-service-build

WORKDIR /app

COPY gradlew build.gradle ./
COPY gradle ./gradle

RUN chmod +x gradlew

RUN ["./gradlew", "dependencies", "--no-daemon"]

COPY src ./src
RUN ["./gradlew", "build", "--no-daemon"]

# RUNTIME STAGE
FROM eclipse-temurin:17-jre-alpine

ENV BOOKS_BACK_SERVER_PORT=${BOOKS_BACK_SERVER_PORT:-8080}

WORKDIR /app

COPY --from=books-service-build --exclude=*-plain.jar /app/build/libs/*.jar ./app.jar

EXPOSE ${BOOKS_BACK_SERVER_PORT}

CMD ["java", "-jar", "./app.jar"]