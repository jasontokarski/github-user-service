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