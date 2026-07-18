# Build stage
FROM gradle:8.12.0-jdk21-alpine AS builder

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon || true
COPY src ./src

# Build the application
RUN gradle bootJar --no-daemon && java -Djarmode=tools -jar build/libs/*.jar extract --layers --destination build/extracted

#Runtime stage with distroless container
FROM gcr.io/distroless/java21-debian12:nonroot

WORKDIR /app

# Copy the extracted layers from builder
COPY --from=builder /app/build/extracted/dependencies/ ./
COPY --from=builder /app/build/extracted/spring-boot-loader/ ./
COPY --from=builder /app/build/extracted/snapshot-dependencies/ ./
COPY --from=builder /app/build/extracted/application/ ./

EXPOSE 8080

# Use non-root user (distroless nonroot user is 65532)
USER 65532:65532

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
