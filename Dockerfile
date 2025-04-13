# STEP 1: Build the application
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app
COPY --no-cache . .

# Build only the bootJar, skip tests for speed
RUN gradle bootJar -x test

# STEP 2: Create minimal runtime image
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
