# Oponeo Order System

This is a recruitment project for Oponeo — a backend system for managing customers, products, and orders in a simplified e-commerce scenario.

## ✨ Features

- ✅ **Customer registration** with contact and address data  
- ✅ **Product catalog management** including net price and VAT  
- ✅ **Order placement** with multiple items and automatic net/gross value calculation  
- ✅ **RESTful API** designed with clear separation: Controller → Service → Repository  
- ✅ **Transactional integrity** for order processing  
- ✅ **Validation** for input data using Jakarta Bean Validation  
- ✅ **Custom error handling** with UUID-based error IDs  
- ✅ **Minimal logging** in key service operations  

## 🛠 Tech Stack

- **Java 17**  
- **Spring Boot** (Web, Validation)  
- **Spring Data JPA**  
- **PostgreSQL** (main DB)  
- **Testcontainers** (integration tests)  
- **JUnit 5** / **Mockito**
- **RestAssured**
- **MapStruct** (domain ↔ entity ↔ DTO mapping)  
- **Swagger UI (OpenAPI 3)**  

## 🔍 API Documentation

Once the application is running, access Swagger UI at:

```
http://localhost:8190/oponeo/swagger-ui/index.html
```

## 🐳 Run with Docker

To run the application using Docker (along with a PostgreSQL database):

### 1. Environment Configuration

Create a `.env` file in the project root with the following content:

```
DB_URL=jdbc:postgresql://postgresql:5432/your_db
DB_USERNAME=your_user
DB_PASSWORD=your_password
```

> These variables are used by `application.yml` to connect to the database container defined in `docker-compose.yml`.

### 2. Build the Docker image
```bash
./gradlew bootJar
docker build -t oponeo-order-system .
```

### 3. Run with Docker Compose
Make sure you have `docker-compose.yml` configured. Then run:

```bash
docker compose up
```

This will start:
- the Spring Boot application on `localhost:8190`
- a PostgreSQL database container

### 4. Stop the containers
```bash
docker compose down
```

## 🧪 Running Tests

This project includes both unit and integration tests.

To run all tests:
```bash
./gradlew test
```

---

## ℹ️ Notes

This is an MVP implementation focused on clear architecture and meeting business requirements.
