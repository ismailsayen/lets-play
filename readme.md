# Let's Play

Let's Play is a REST API built with Spring Boot, MongoDB, and Spring Security. It allows managing users and products, with JWT-based authentication and two roles:

- `ADMIN`: access to user management and all products
- `USER`: create and manage only their own products

Passwords are stored using BCrypt and are not returned in API responses.

## Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- MongoDB (the easiest option is to use the Docker service included in this project)

## Configuration

At the project root, copy the example file:

```bash
cp .env.example .env
```

Then fill in the variables in `.env`:

```dotenv
DB_USERNAME=letsplay
DB_PASSWORD=change-me
DB_NAME=letsplay

adminName=admin
adminPass=change-me
adminEmail=admin@example.com

secret_key=YOUR_BASE64_KEY
```

`secret_key` must be a sufficiently long Base64 key used to sign JWTs.
For example, it can be generated with:

```bash
openssl rand -base64 32
```

Never commit the `.env` file.

## Run the application

1. Start MongoDB:

   ```bash
   docker compose up -d
   ```

2. Start Spring Boot:

   ```bash
   ./mvnw spring-boot:run
   ```

   On Windows:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

The API is available at `http://localhost:8080/api`.

To stop MongoDB:

```bash
docker compose down
```

MongoDB data is kept in the Docker volume `mongodb_data`.

## Main endpoints

All routes below are prefixed with `/api`.

### Authentication

| Method | Route | Access | Description |
| --- | --- | --- | --- |
| `POST` | `/auth/register` | Public | Create a user account |
| `POST` | `/auth/login` | Public | Log in and receive a JWT |

Registration example:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","email":"alice@example.com","password":"password123"}'
```

Login example:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"alice@example.com","password":"password123"}'
```

The token is returned in the `Token` field of the response. For protected routes,
send it in the header:

```text
Authorization: Bearer <your-jwt>
```

### Products

| Method | Route | Access | Description |
| --- | --- | --- | --- |
| `GET` | `/products` | Public | List products |
| `POST` | `/products` | Authenticated | Create a product |
| `PUT` | `/products/{id}` | Owner or admin | Update a product |
| `DELETE` | `/products/{id}` | Owner or admin | Delete a product |

Create product example:

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Authorization: Bearer <your-jwt>" \
  -H "Content-Type: application/json" \
  -d '{"name":"Board game","description":"A family game","price":"29.99"}'
```

### Users

These routes are reserved for administrators:

| Method | Route | Description |
| --- | --- | --- |
| `GET` | `/users` | List users |
| `GET` | `/users/{id}` | Get a user |
| `PUT` | `/users/{id}` | Update a user |
| `DELETE` | `/users/{id}` | Delete a user |

An admin account is created at startup from the `adminName`, `adminEmail`, and
`adminPass` variables.

## Validation and errors

Incoming data is validated by the application. Common errors return the following
HTTP statuses:

- `400 Bad Request`: invalid data or malformed request
- `401 Unauthorized`: missing or invalid authentication
- `403 Forbidden`: insufficient permissions
- `404 Not Found`: resource not found
- `409 Conflict`: email, username, or resource already exists

## Tests and build

Run tests:

```bash
./mvnw test
```

Build the project:

```bash
./mvnw clean package
```

## Project structure

```text
src/main/java/isayen/lets_play/
├── auth/       # signup and login
├── config/     # JWT security and global error handling
├── products/   # product model, service, and routes
├── users/      # user model, service, and routes
└── bootstrap/  # initial admin creation
```

# Spring Boot HTTPS Setup Guide

## Setup in Spring Boot (Step by step)

### Step 1: Generate a local keystore for development
Run the following command in your terminal to create a self-signed certificate file:

```bash
keytool -genkeypair -alias springboot -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 365
```

Set a secure password when prompted, for example `secretpassword`.

### Step 2: Place the keystore in the project
Copy the generated `keystore.p12` file and place it in the `src/main/resources/` folder of your Spring Boot project.

### Step 3: Configure the `application.properties` file
Add the following properties to enable HTTPS and define the secure port (usually `8443` locally):

```properties
server.port=8443
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=your_password
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=springboot
```
