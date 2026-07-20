# GitHub User Service

A Spring Boot microservice that fetches GitHub user information and repositories using the GitHub API.

## Features

- RESTful API to retrieve GitHub user details and repositories
- Concurrent API calls using Java 21 Virtual Threads
- Retry mechanism with exponential backoff
- Global exception handling
- Input validation
- Actuator endpoints for monitoring

## Technologies and Libraries

- **Java 21** with Virtual Threads
- **Spring Boot 4.1.0**
- **Gradle 8.12**
- **Lombok** for boilerplate reduction
- **Spring Retry** for resilience
- **Spring Actuator** for monitoring
- **JaCoCo** for code coverage

## API Endpoints

### Get GitHub User and Repositories

```
GET /api/v1/github/users/{username}
```

**Response:**
```json
{
  "user_name": "octocat",
  "display_name": "The Octocat",
  "avatar": "https://avatars.githubusercontent.com/u/583231",
  "geo_location": "San Francisco",
  "email": "octocat@github.com",
  "url": "https://api.github.com/users/octocat",
  "created_at": "Tue, 25 Jan 2011 18:44:36 GMT",
  "repos": [...]
}
```

**Status Codes:**
- `200 OK` - Success
- `400 BAD REQUEST` - Invalid username format
- `404 NOT FOUND` - User not found
- `429 TOO MANY REQUESTS` - Rate limit exceeded
- `503 SERVICE UNAVAILABLE` - GitHub API unavailable

## Prerequisites

- Java 21 or later
- Docker (for containerized deployment)

## Building the Application

### Manual Build (Local)

#### Prerequisites
- Java 21 JDK installed

#### Step-by-Step Build Process

**Clone the repository**
```bash
git clone https://github.com/jasontokarski/github-user-service
cd githubuserservice
```

**Build the application**:

On Linux/macOS:
```bash
chmod +x gradlew
./gradlew clean build
```

On Windows:
```bash
gradlew.bat clean build
```

**Build outputs**:
- Compiled classes: `build/classes/`
- JAR file: `build/libs/githubuserservice-0.0.1-SNAPSHOT.jar`
- Test reports: `build/reports/tests/test/index.html`
- Test coverage: `build/reports/jacoco/test/html/index.html`

#### Additional Build Commands

```bash
# Run tests only
./gradlew test

# Generate test coverage report
./gradlew jacocoTestReport
```

### Docker Build

The application uses a multi-stage Docker build with a distroless base image for security and minimal footprint.

#### Using Docker

```bash
docker build -t githubuserservice:latest .
# Build with custom tag
docker build -t githubuserservice:v1.0.0 .
```

The Dockerfile uses a **multi-stage build** for optimal image size and security:

**Build Stage**: Uses `gradle:8.14-jdk21-alpine` image to:
   - Download dependencies (cached for faster rebuilds)
   - Compile the application
   - Create a bootable JAR

**Runtime Stage**: Uses `gcr.io/distroless/java21-debian12:nonroot` to:
   - Copy only the JAR file (minimal attack surface)
   - Run as non-root user for security
   - Expose port 8080

**Benefits:**
- **Small image**: ~218MB (compared to 500MB+ with full JDK)
- **Secure**: Distroless base with no shell or package manager
- **Fast rebuilds**: Docker caches dependency layer
- **Non-root**: Runs as user `nonroot` (UID 65532)

#### Verify Docker Build

```bash
# List images
docker images | grep githubuserservice

# Inspect image details
docker inspect githubuserservice:latest
```

## Running the Application

### Local Execution

```bash
# Run with Gradle
./gradlew bootRun

# Or run the JAR directly
java -jar build/libs/githubuserservice-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

### Docker Execution

```bash
docker run -p 8080:8080 githubuserservice:latest
```

### Available Profiles

- **dev** (default) - Development environment with debug logging and all actuator endpoints enabled
- **staging** - Staging environment with standard logging and limited actuator endpoints
- **prod** - Production environment with minimal logging, restricted endpoints, and error details hidden

### Running with Specific Profiles

#### Using Gradle

```bash
# Development (default)
./gradlew bootRun

# Staging
./gradlew bootRun --args='--spring.profiles.active=staging'

# Production
./gradlew bootRun --args='--spring.profiles.active=prod'
```

#### Using JAR

```bash
# Development (default)
java -jar build/libs/githubuserservice-0.0.1-SNAPSHOT.jar

# Staging
java -jar build/libs/githubuserservice-0.0.1-SNAPSHOT.jar --spring.profiles.active=staging

# Production
java -jar build/libs/githubuserservice-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

#### Docker with Profiles

```bash
# Development
docker run -p 8080:8080 githubuserservice:latest

# Staging
docker run -p 8080:8080 -e SPRING_PROFILE=staging githubuserservice:latest

# Production
docker run -p 8080:8080 -e SPRING_PROFILE=prod githubuserservice:latest
```