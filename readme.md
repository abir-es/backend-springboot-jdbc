# Backend Spring Boot JDBC Application

This project is a Spring Boot application that uses JDBC to interact with a PostgreSQL database. It provides a RESTful API for managing products, channels, product offering prices, and product offering relationships.

## Table of Contents

- [Requirements](#requirements)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Swagger Documentation](#swagger-documentation)
- [Project Structure](#project-structure)

## Requirements

- Java 17 or higher
- PostgreSQL 16 or higher
- Maven 3.8 or higher
- Spring boot 3.2.5

## Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/backend-springboot-jdbc.git
   cd backend-springboot-jdbc
   ```

2. **Build the project:**

   ```bash
   mvn clean install
   ```

3. **Run the application:**

   ```bash
   mvn spring-boot:run
   ```

## Configuration

### Database Configuration

Ensure you have a PostgreSQL database running. Update the `application.properties` file with your database details:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/catalogue_svc
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```

### Log Configuration

The application uses Log4j2 for logging. The configuration can be found in the `log4j2.properties` file:

```properties
status = error
name = PropertiesConfig

property.filename = logs/application.log

appenders = console, file

appender.console.type = Console
appender.console.name = STDOUT
appender.console.layout.type = PatternLayout
appender.console.layout.pattern = [%d{yyyy-MM-dd HH:mm:ss}] %-5p %c{1}:%L - %m%n

appender.file.type = File
appender.file.name = LOGFILE
appender.file.fileName = ${filename}
appender.file.layout.type = PatternLayout
appender.file.layout.pattern = [%d{yyyy-MM-dd HH:mm:ss}] %-5p %c{1}:%L - %m%n

rootLogger.level = info
rootLogger.appenderRefs = stdout, LOGFILE
rootLogger.appenderRef.stdout.ref = STDOUT
rootLogger.appenderRef.LOGFILE.ref = LOGFILE
```

## Usage

Once the application is running, you can access the API at `http://localhost:9090/api`.

### Sample API Calls

- **Get all products:**
  ```bash
  curl -X GET http://localhost:9090/api/products
  ```

- **Get a product by ID:**
  ```bash
  curl -X GET http://localhost:9090/api/products/{id}
  ```

- **Create a new product:**
  ```bash
  curl -X POST http://localhost:9090/api/products \\
  -H "Content-Type: application/json" \\
  -d '{
  "name": "New Product",
  "description": "Product Description",
  ...
  }'
  ```

- **Update a product:**
  ```bash
  curl -X PUT http://localhost:9090/api/products/{id} \\
  -H "Content-Type: application/json" \\
  -d '{
  "name": "Updated Product",
  "description": "Updated Description",
  ...
  }'
  ```

- **Delete a product:**
  ```bash
  curl -X DELETE http://localhost:9090/api/products/{id}
  ```

## API Endpoints

| Method | Endpoint                         | Description                          |
|--------|----------------------------------|--------------------------------------|
| GET    | /api/products                 | Retrieve all products                |
| GET    | /api/products/{id}            | Retrieve a product by ID             |
| POST   | /api/products                 | Create a new product                 |
| PUT    | /api/products/{id}            | Update a product by ID               |
| DELETE | /api/products/{id}            | Delete a product by ID               |


## Swagger Documentation
Swagger is integrated to provide API documentation.

API Docs: `http://localhost:9090/v3/api-docs`

Swagger UI: `http://localhost:9090/swagger-ui.html`

## Project Structure

```
src/
├── main/
│   ├── java/com/practice/backend/
│   │   ├── controller/         # REST controllers
│   │   ├── dao/                # Data Access Objects
│   │   ├── entity/             # Entity models
│   │   ├── service/            # Service layer
│   │   └── BackendApplication.java  # Main Spring Boot application
│   └── resources/
│       ├── application.properties  # Application configuration
│       └── log4j2.properties       # Logging configuration
└── test/
└── java/com/practice/backend/  # Unit and integration tests
```

