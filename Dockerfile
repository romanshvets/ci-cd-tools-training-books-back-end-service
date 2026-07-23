# BUILD STAGE
FROM gradle:jdk17-alpine AS books-service-build

WORKDIR /app

COPY gradlew build.gradle ./
COPY gradle ./gradle
RUN ["./gradlew", "dependencies", "--no-daemon"]

COPY src ./src
RUN ["./gradlew", "build", "--no-daemon"]

# RUNTIME STAGE
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=books-service-build --exclude=*-plain.jar /app/build/libs/*.jar ./app.jar

EXPOSE 8080

CMD ["java", "-jar", "./app.jar"]