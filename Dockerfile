# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS MAVEN_BUILD

WORKDIR /app

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package

# Stage 2: Create the final image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/payment-api.jar /app/

ENTRYPOINT ["java", "-jar", "payment-api.jar"]