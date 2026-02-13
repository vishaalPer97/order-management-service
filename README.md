# Order Management Service

A RESTful microservice built with Spring Boot for managing orders. This service provides a complete order lifecycle management system with status transitions and validation.

## Features

- **Order Creation**: Create new orders with customer details and amounts
- **Order Retrieval**: Fetch individual orders or retrieve all orders
- **Status Management**: Update order status with built-in validation for state transitions
- **In-Memory Storage**: Fast, thread-safe in-memory data storage using ConcurrentHashMap
- **API Documentation**: Interactive API documentation via Swagger UI
- **Health Monitoring**: Spring Actuator endpoints for application monitoring
- **Input Validation**: Request validation using Jakarta Bean Validation
- **Exception Handling**: Global exception handling with meaningful error responses

## Technology Stack

- **Java**: 17
- **Spring Boot**: 4.0.2
- **Build Tool**: Maven
- **Libraries**:
  - Spring Web MVC
  - Spring Actuator
  - Lombok
  - Jakarta Validation
  - SpringDoc OpenAPI

## Project Structure

```
order-mgmt-service/
├── src/
│   ├── main/
│   │   ├── java/com/order/mgmt/service/
│   │   │   ├── controller/         # REST API endpoints
│   │   │   ├── service/            # Business logic layer
│   │   │   ├── repository/         # Data access layer
│   │   │   ├── entities/           # Domain models
│   │   │   ├── dto/                # Data Transfer Objects
│   │   │   ├── exceptions/         # Custom exceptions and handlers
│   │   │   └── OrderMgmtServiceApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/                       # Test classes
└── pom.xml
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Installation

1. Clone the repository
```bash
git clone https://github.com/vishaalPer97/order-management-service.git
cd order-mgmt-service
```

2. Build the project
```bash
mvn clean install
```

3. Run the application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Create Order
```http
POST /orders
Content-Type: application/json

{
  "customerName": "John Doe",
  "amount": 150.00
}
```

**Response**: `201 Created`
```json
{
  "orderId": "ORD-a1b2c3d4e5f6...",
  "customerName": "John Doe",
  "amount": 150.00,
  "status": "NEW"
}
```

### Get Order by ID
```http
GET /orders/{orderId}
```

**Response**: `200 OK`
```json
{
  "orderId": "ORD-a1b2c3d4e5f6...",
  "customerName": "John Doe",
  "amount": 150.00,
  "status": "NEW"
}
```

### Get All Orders
```http
GET /orders
```

**Response**: `200 OK`
```json
[
  {
    "orderId": "ORD-a1b2c3d4e5f6...",
    "customerName": "John Doe",
    "amount": 150.00,
    "status": "NEW"
  }
]
```

### Update Order Status
```http
PUT /orders/{orderId}/status
Content-Type: application/json

{
  "orderStatus": "PROCESSING"
}
```

**Response**: `200 OK`
```json
{
  "orderId": "ORD-a1b2c3d4e5f6...",
  "customerName": "John Doe",
  "amount": 150.00,
  "status": "PROCESSING"
}
```

## Order Status Flow

Orders follow a strict state machine pattern:

```
NEW → PROCESSING → COMPLETED
```

**Valid Transitions**:
- NEW → PROCESSING ✓
- PROCESSING → COMPLETED ✓

**Invalid Transitions** (will return error):
- NEW → COMPLETED ✗
- PROCESSING → NEW ✗
- COMPLETED → any status ✗

## API Documentation

Once the application is running, access the interactive API documentation at:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## Monitoring Endpoints

Spring Actuator provides several monitoring endpoints:

- **Health**: http://localhost:8080/actuator/health
- **Info**: http://localhost:8080/actuator/info
- **All Endpoints**: http://localhost:8080/actuator

## Error Handling

The service provides comprehensive error handling with appropriate HTTP status codes:

| Error | Status Code | Description |
|-------|-------------|-------------|
| Order Not Found | 404 | Requested order ID does not exist |
| Invalid Status Transition | 400 | Attempted invalid status update |
| Validation Error | 400 | Invalid request body (missing/invalid fields) |

**Error Response Format**:
```json
{
  "message": "Error description",
  "timestamp": "2026-02-13T10:30:00"
}
```

## Data Validation

### Create Order Request
- `customerName`: Required, cannot be blank
- `amount`: Required, must be positive (> 0)

### Update Status Request
- `orderStatus`: Required, must be valid enum value (NEW, PROCESSING, COMPLETED)

## Testing

Run the test suite:
```bash
mvn test
```

## Configuration

The application can be configured via `src/main/resources/application.properties`:

```properties
spring.application.name=order-mgmt-service
management.endpoints.web.exposure.include=*
management.info.env.enabled=true
info.app.name=Order Management Service
info.app.version=v1.0.0
```

## Architecture

The service follows a layered architecture:

1. **Controller Layer**: Handles HTTP requests and responses
2. **Service Layer**: Contains business logic and validation
3. **Repository Layer**: Manages data persistence (in-memory)

### Key Components

- **OrderController**: REST API endpoints
- **OrderService/OrderServiceImpl**: Business logic and status validation
- **OrderRepository/InMemoryOrderRepository**: In-memory data storage
- **GlobalExceptionHandler**: Centralized exception handling
- **Order**: Main domain entity
- **OrderStatus**: Enum defining order states

## Notes

- Data is stored in-memory and will be lost when the application restarts
- The in-memory storage uses `ConcurrentHashMap` for thread-safety
- Order IDs are automatically generated with UUID and prefixed with "ORD-"
- All new orders start with status "NEW"

## License

This project is provided as-is for educational and demonstration purposes.
