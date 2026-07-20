# Build stage
FROM gradle:8.14-jdk21-alpine AS builder

WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon || true
COPY src ./src

# Build the application
RUN gradle bootJar --no-daemon

# Runtime stage with distroless container
FROM gcr.io/distroless/java21-debian12:nonroot

WORKDIR /app

# Copy the JAR from builder
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

# Run as non-root user (distroless default)
USER nonroot:nonroot

ENTRYPOINT ["java", "-jar", "app.jar"]
