# Spring Boot Data Loader and Form Submission Service

## Overview

This project is a Spring Boot service designed to:

1. Load service configurations from a NoSQL database (MongoDB).
2. Submit forms to a SQL database (PostgreSQL).
3. Validate submissions against the loaded configurations using custom validation logic.
4. Perform DTO validation.
5. Publish submission events to a Kafka topic.
6. Provide secure API access using JWT and Spring Security.
7. Integrate Swagger for API documentation.
8. Include a Postman collection for testing the API.
9. initiate the Mongo db by loadig data at starting from a Json file. 

## Features

- **NoSQL and SQL Integration**:
    - Service configurations are loaded from MongoDB.
    - Form submissions are stored in PostgreSQL.
- **Custom Validation**:
    - Validates submissions against configurations.
    - Ensures DTOs meet specified requirements.
- **Event Publishing**:
    - Submissions are published to a Kafka topic.
- **Security**:
    - Secured using JWT and Spring Security.
- **API Documentation**:
    - Swagger UI available at `http://localhost:7777/swagger-ui.html`.
- **Docker Support**:
    - Includes a Docker Compose file to run the stack.
    - Services include Kafka, PostgreSQL, MongoDB, Adminer, and Mongo Express.
- **Postman Collection**:
    - Pre-configured collection for testing API endpoints.

## Prerequisites

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Postman](https://www.postman.com/)

## Getting Started

### Clone the Repository

```bash
git clone <repository-url>
cd <repository-folder>
```

### Running the Stack

Use Docker Compose to start the services:

```bash
docker-compose up -d
```

The following services will be available:

- **Kafka**: Message broker
- **PostgreSQL**: SQL database
- **Adminer**: PostgreSQL admin interface (accessible at `http://localhost:8080`)
- **MongoDB**: NoSQL database
- **Mongo Express**: MongoDB admin interface (accessible at `http://localhost:8081`)

### Accessing Swagger UI

Swagger documentation is available at:

```
http://localhost:7777/swagger-ui.html
```

### Testing with Postman

1. Import the provided Postman collection (`postman-collection.json`) into Postman.
2. Configure the base URL and authentication token in Postman.
3. Test the API endpoints.

## Exposed Endpoints

### Service Configuration

- **Load All Configurations**
    - `GET /api/service-config/configurations`
- **Load Configuration by ID**
    - `GET /api/service-config/configurations/{id}`

### Submission

- **Create Submission**
    - `POST /api/submissions`
- **Get All Submissions**
    - `GET /api/submissions`
- **Get Submissions by Service ID**
    - `GET /api/submissions/service/{serviceId}`
- **Get Submissions by Customer ID**
    - `GET /api/submissions/customer/{customerId}`

### User Management

- **Register a New User**
    - `POST /api/auth/register`
- **Authenticate User**
    - `POST /api/auth/authenticate`

## Configuration

### Environment Variables

| Variable             | Description                  | Default Value       |
|----------------------|------------------------------|---------------------|
| `JWT_SECRET`         | Secret key for JWT          | `your_jwt_secret`   |
| `SPRING_PROFILES`    | Spring profiles (e.g., dev) | `dev`               |
| `POSTGRES_HOST`      | PostgreSQL hostname         | `localhost`         |
| `POSTGRES_PORT`      | PostgreSQL port             | `5432`              |
| `MONGO_HOST`         | MongoDB hostname            | `localhost`         |
| `MONGO_PORT`         | MongoDB port                | `27017`             |

## Dependencies

- **Spring Boot**
- **Spring Security**
- **MongoDB Driver**
- **PostgreSQL Driver**
- **Spring Kafka**
- **Swagger/OpenAPI**
- **Docker Compose**

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
