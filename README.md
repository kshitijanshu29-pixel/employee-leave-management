# Employee Leave Management System

A production-ready REST API built with Spring Boot for managing employees, user authentication, leave requests, and role-based access control.

The application implements JWT-based authentication, role-based authorization, DTO-based request/response handling, validation, pagination, exception handling, database migrations with Flyway, automated testing, and cloud deployment.

## 🚀 Live API

**Production API:**

https://employee-leave-management-production-b072.up.railway.app

> The API is deployed on Railway and connected to a Railway MySQL database.

---

## 📌 Features

### Employee Management

- Create employees
- Get all employees
- Get employee by ID
- Search employees by name
- Update employee details
- Delete employees
- Pagination and sorting
- Request validation

### Authentication & Authorization

- User registration
- JWT-based authentication
- BCrypt password hashing
- Role-based authorization
- `ADMIN` and `EMPLOYEE` roles
- Protected API endpoints
- Employee accounts linked to employee records
- Admin bootstrap for initial production administrator

### Leave Management

- Employees can create leave requests
- Employees can view their own leave requests
- Administrators can view all leave requests
- Administrators can update leave request status
- Role-based access control for leave operations

### Database & Persistence

- MySQL database
- Spring Data JPA / Hibernate
- Flyway database migrations
- Entity relationships
- Production database hosted on Railway

### Validation & Error Handling

- Jakarta Bean Validation
- Custom exceptions
- Global exception handling
- Appropriate HTTP status codes
- Duplicate employee/account checks
- Invalid request handling

### Testing

- JUnit
- Mockito
- Service-layer unit tests
- Repository/service/controller functionality tested during development
- Production API testing using Thunder Client

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 25 | Programming language |
| Spring Boot 4.1.1 | Backend framework |
| Spring Web | REST API |
| Spring Data JPA | Database persistence |
| Spring Security | Authentication & authorization |
| JWT | Stateless authentication |
| BCrypt | Password hashing |
| MySQL | Relational database |
| Flyway | Database migrations |
| Maven | Build & dependency management |
| JUnit | Testing |
| Mockito | Mock-based testing |
| Thunder Client | API testing |
| Railway | Cloud deployment |
| Git & GitHub | Version control |

---

## 🏗️ Architecture

The application follows a layered architecture:

```text
Client
  │
  ▼
Controller
  │
  ▼
Service
  │
  ▼
Repository
  │
  ▼
MySQL Database
