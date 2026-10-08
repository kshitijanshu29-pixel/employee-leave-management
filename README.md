# Employee Leave Management System

A production-deployed REST API built with Spring Boot for managing employees, user authentication, leave requests, and role-based access control.

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

Authentication is handled through Spring Security and JWT:
Client
  │
  │ Login
  ▼
Auth Controller
  │
  ▼
Authentication Service
  │
  ▼
JWT
  │
  ▼
Client
  │
  │ Authorization: Bearer <JWT>
  ▼
JWT Authentication Filter
  │
  ▼
Spring Security
  │
  ▼
Controller

👥 Roles
The application currently supports two roles.
ADMIN
Administrators can:
- Manage employees
- View all leave requests
- Update leave request status
- Perform administrative operations
EMPLOYEE
Employees can:
- Authenticate using their account
- View their own information
- Create leave requests
- View their own leave requests
🔐 Authentication
The application uses JWT-based stateless authentication.
Login : POST /auth/login
Example request:
{
  "username": "username",
  "password": "password"
}

Example response:
{
  "username": "username",
  "token": "JWT_TOKEN",
  "role": "ROLE_EMPLOYEE"
}

The returned token is then supplied with protected requests:
Authorization: Bearer <JWT_TOKEN>

Passwords are stored using BCrypt hashing rather than plaintext storage.
📝 Registration
Employee registration is available through:
POST /auth/register

Registration creates an employee user account and assigns the EMPLOYEE role.
Example:
{
  "username": "employee",
  "password": "password",
  "employeeNumber": 1002
}

The employee number is used to associate the user account with an existing employee record.
Public registration cannot be used to create an administrator account.
👨‍💼 Employee API
Create Employee
POST /employee

Requires:
ROLE_ADMIN

Example:
{
  "name": "Ajaya Routray",
  "employeeNumber": 1002,
  "department": "Engineering",
  "position": "Frontend Developer",
  "email": "ajayaroutray@example.com"
}

Get All Employees
GET /Allemployees/pages?page=0&size=10&sort=name&direction=asc

Requires:
ROLE_EMPLOYEE
ROLE_ADMIN

Example:
?page=0
&size=10
&sort=name
&direction=asc

Supports pagination, sorting, and direction selection.
Get Employee By ID
GET /Getemployee/{id}

Requires:
ROLE_EMPLOYEE
ROLE_ADMIN

Search Employee By Name
GET /Getemployees/name?name={name}

Requires:
ROLE_EMPLOYEE
ROLE_ADMIN

Update Employee
PUT /Updateemployee/{id}

Requires:
ROLE_ADMIN

Delete Employee
DELETE /Deleteemployee/{id}

Requires:
ROLE_ADMIN

🏖️ Leave API
Create Leave Request
POST /leave

Requires:
ROLE_EMPLOYEE
ROLE_ADMIN

Example structure:
{
  "leaveType": "SICK",
  "startDate": "2026-10-08",
  "endDate": "2026-10-15",
  "cause": "Having viral fever
}

Date values use the YYYY-MM-DD format required by Java LocalDate.

Additional fields depend on the CreateLeaveRequest DTO implemented in the application.
Get My Leave Requests
GET /my-leaves

Requires:
ROLE_EMPLOYEE

Returns leave requests associated with the authenticated employee.
Get All Leave Requests
GET /Allleaverequests/pages?page=0&size=10

Requires:
ROLE_ADMIN

Supports pagination.
Update Leave Status
PUT /updaterequest/{id}/status?status={status}

Requires:
ROLE_ADMIN

The administrator can update the status of a leave request.
🗄️ Database
The application uses MySQL with Spring Data JPA.
Main entities include:
Employee
   │
   │
   └──── User
           │
           └──── Role

Employee
   │
   └──── LeaveRequest

The database schema is managed using Flyway migrations.
🔄 Database Migration
Flyway is used to manage database schema changes.
Migration files are stored under:
src/main/resources/db/migration/

The initial migration creates the required application tables and relationships.
In production:
spring.jpa.hibernate.ddl-auto=validate

This prevents Hibernate from automatically modifying the production schema.
Flyway is responsible for applying schema changes.
⚙️ Configuration
Sensitive configuration is supplied through environment variables rather than being committed to Git.
Production configuration uses variables such as:
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET

The initial administrator bootstrap also uses:
ADMIN_USERNAME
ADMIN_PASSWORD
ADMIN_EMPLOYEE_NUMBER

Secrets and passwords should never be committed to the repository.
💻 Running Locally
Prerequisites
Install:
- Java 25
- Maven or use the included Maven Wrapper
- MySQL
- Git
Clone the Repository
git clone https://github.com/kshitijanshu29-pixel/employee-leave-management.git

Enter the project directory:
cd employee-leave-management

Configure Environment Variables
Set the required database and JWT configuration.
Example PowerShell configuration:
$env:DB_URL="jdbc:mysql://localhost:3306/leave_management"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_password"
$env:JWT_SECRET="your_jwt_secret"

For local development, activate the development profile:
$env:SPRING_PROFILES_ACTIVE="dev"

Run the Application
Using Maven Wrapper:
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"

The application will be available at:
http://localhost:8080

🧪 Testing
The project includes unit tests using:
- JUnit
- Mockito
Tests cover important service-layer business logic, validation scenarios, duplicate handling, exception handling, and other application behavior.
The production API was also tested manually using Thunder Client.
Production testing included:
- Admin login
- Employee login
- JWT authentication
- Role-based authorization
- Employee creation
- Employee retrieval
- Employee registration
- Leave creation
- Leave retrieval
- Leave status updates
- Production database persistence
☁️ Deployment
The application is deployed using Railway.
Production architecture:
                    Internet
                       │
                       ▼
              Railway Public Domain
                       │
                       ▼
              Spring Boot Application
                       │
                       │ Private Network
                       ▼
                 Railway MySQL

Application
Hosted on Railway as a Spring Boot service.
Database
MySQL is hosted on Railway and connected to the application through Railway's private networking.
Public API
https://employee-leave-management-production-b072.up.railway.app

Database Security
The MySQL database is not exposed publicly. The Spring Boot application communicates with the database through Railway's private network.
🔒 Security
Security features implemented include:
- JWT authentication
- BCrypt password hashing
- Role-based authorization
- Protected API endpoints
- Stateless authentication
- Environment-based secret management
- Restricted administrator creation
- Employee/user relationship enforcement
Example authorization:
ADMIN
 ├── Employee management
 ├── View all leave requests
 └── Update leave status

EMPLOYEE
 ├── View employee data
 ├── Create leave requests
 └── View own leave requests

📂 Project Structure
src
├── main
│   ├── java
│   │   └── com.kshitij.employee_leave_management
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── exception
│   │       ├── model
│   │       ├── repository
│   │       ├── security
│   │       └── service
│   │
│   └── resources
│       ├── db
│       │   └── migration
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-prod.properties
│
└── test
    └── java

🧠 Key Backend Concepts Demonstrated
This project demonstrates practical implementation of:
- RESTful API design
- Layered architecture
- DTO pattern
- Entity relationships
- Spring Data JPA
- Hibernate
- Spring Security
- JWT authentication
- Role-based authorization
- BCrypt
- Bean Validation
- Global exception handling
- Pagination
- Sorting
- Database migrations
- Unit testing
- Mockito
- Environment-based configuration
- Production deployment
- Cloud database integration
- Private cloud networking
📈 Future Improvements
Potential future improvements include:
- Refresh token support
- Email notifications for leave decisions
- Leave balance management
- Advanced search and filtering
- Swagger/OpenAPI documentation
- Docker-based deployment
- CI/CD pipeline
- Audit logging
- Automated integration tests
- Frontend application
- Admin dashboard
- Employee dashboard
👨‍💻 Author
Kshitij Routray
GitHub:
https://github.com/kshitijanshu29-pixel
⭐ Project Status
Production Deployed
The Employee Leave Management System is deployed on Railway with a cloud MySQL database and publicly accessible REST APIs.
