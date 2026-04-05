# Bone Health AI — Backend

REST API for the Bone Health AI platform built with Spring Boot 3.2 and Java 21. Handles user authentication and authorization using JWT.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.2 |
| Security | Spring Security + JWT (jjwt 0.11.5) |
| Database | H2 (file-based) |
| ORM | Spring Data JPA / Hibernate |
| Build Tool | Maven |

---

## Project Structure

```
src/main/java/com/BoneHealthAI/Backend/
├── config/                  # Security configuration, CORS
├── controller/
│   ├── AuthController.java  # /api/auth/signup, /api/auth/login
│   └── UserController.java  # /api/user/profile
├── dto/
│   ├── SignupRequest.java
│   ├── LoginRequest.java
│   └── AuthResponse.java
├── entity/
│   └── User.java            # User entity (id, name, email, password, role)
├── repository/
│   └── UserRepository.java
├── security/
│   └── JwtAuthFilter.java   # JWT validation filter
├── service/
│   ├── AuthService.java
│   └── UserDetailsServiceImpl.java
└── util/
    └── JwtUtil.java         # Token generation and validation
```

---

## Getting Started

### Prerequisites

- Java 21
- Maven 3.8+

### Clone and Run

```bash
git clone https://github.com/C0deHungeR/Bone_Health_AI_Backend.git
cd Bone_Health_AI_Backend/Backend
./mvnw spring-boot:run
```

The server starts at `http://localhost:8080`.

---

## Configuration

All config is in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:h2:file:./data/authdb
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

jwt.secret=mySuperSecretKeyThatIsAtLeast32CharsLong!
jwt.expiration=86400000
```

> **Note:** Change `jwt.secret` to a strong random string before deploying.

H2 Console is available at `http://localhost:8080/h2-console`  
JDBC URL: `jdbc:h2:file:./data/authdb`

---

## API Endpoints

### Auth (Public)

#### POST `/api/auth/signup`
Register a new user.

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "secret123",
  "role": "PATIENT"
}
```
> `role` accepts `PATIENT` or `DOCTOR` only.

**Response:**
```json
{
  "token": "<jwt_token>",
  "email": "john@example.com",
  "name": "John Doe",
  "role": "PATIENT"
}
```

---

#### POST `/api/auth/login`
Login with existing credentials.

**Request Body:**
```json
{
  "email": "john@example.com",
  "password": "secret123"
}
```

**Response:**
```json
{
  "token": "<jwt_token>",
  "email": "john@example.com",
  "name": "John Doe",
  "role": "PATIENT"
}
```

---

### User (Protected)

#### GET `/api/user/profile`
Returns the logged-in user's details.

**Header:**
```
Authorization: Bearer <jwt_token>
```

**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "PATIENT"
}
```

---

## Authentication Flow

```
Client → POST /api/auth/login → Server returns JWT
Client → GET /api/user/profile (with Bearer token) → Server validates JWT → Returns data
```

JWT tokens are valid for **24 hours** (`jwt.expiration=86400000` ms).

---

## CORS

Configured to allow requests from `http://localhost:3000` (Next.js frontend).  
Update the allowed origin in `SecurityConfig.java` when deploying to production.

---

## Roles

| Role | Value |
|---|---|
| Patient | `PATIENT` |
| Doctor | `DOCTOR` |

Role-based access control (RBAC) for doctor/patient specific endpoints will be added in future iterations using `@PreAuthorize`.

---

## Future Scope

- Role-based endpoints (doctor dashboard, patient reports)
- Scan upload and result storage
- Switch to PostgreSQL for production
- Refresh token support
- Dockerize the application
