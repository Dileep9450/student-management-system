# Student Management System

A secure RESTful Student Management System built using **Java, Spring Boot, Spring Data JPA, Hibernate, MySQL, Spring Security, and JWT**.

The application provides APIs for managing student records with authentication, role-based authorization, validation, exception handling, searching, pagination, sorting, Swagger documentation, logging, and automated testing.

---

## 📌 Project Overview

The Student Management System is designed as a backend REST API that provides secure and reliable student record management.

The application follows a layered architecture:

```text
Client
   |
   v
REST API
   |
   v
Controller Layer
   |
   v
Service Layer
   |
   v
Repository Layer
   |
   v
MySQL Database

Authentication and authorization are handled using Spring Security and JWT.

Client
   |
   | Login
   v
AuthController
   |
   v
LoginService
   |
   v
JWT Token
   |
   v
Client
   |
   | Authorization: Bearer <JWT_TOKEN>
   v
JWT Authentication Filter
   |
   v
Spring Security
   |
   v
Protected REST APIs
🚀 Features
Student Management
Create student
Get all students
Get student by roll number
Update student
Delete student
Find student by email
Search
Search students by name
Search students by course
Search students by name and course
Pagination and Sorting
Pagination support
Dynamic sorting
Sort by different student fields
Ascending and descending sorting
Authentication
User registration
User login
JWT-based authentication
JWT token validation
Stateless authentication
BCrypt password hashing
Authorization
USER role
ADMIN role
Role-based access to student APIs
Validation
Request validation using Jakarta Bean Validation
Required field validation
Email format validation
Phone number validation
Exception Handling
Global exception handling
Custom exceptions
Structured error responses
Duplicate email handling
Duplicate username handling
Invalid credentials handling
Resource not found handling
Development and Testing
Swagger/OpenAPI documentation
SLF4J logging
Unit testing
Mockito
JUnit 5
MockMvc controller testing
Integration testing
🛠️ Technologies Used
Backend
Java 25
Spring Boot 3.5.5
Spring Web
Spring Data JPA
Hibernate
Spring Security
JWT
BCrypt
MySQL
Maven
Lombok
Testing
JUnit 5
Mockito
MockMvc
Spring Boot Test
API Documentation
Swagger
OpenAPI
Version Control
Git
GitHub
🏗️ Architecture

The project follows a layered architecture.

com.dileep.studentmanagement
│
├── config
│   ├── OpenAPIConfig
│   └── SecurityConfig
│
├── controller
│   ├── AuthController
│   └── StudentController
│
├── dto
│   ├── LoginRequest
│   ├── LoginResponse
│   ├── RegisterRequest
│   ├── RegisterResponse
│   ├── StudentRequest
│   └── StudentResponse
│
├── entity
│   ├── Student
│   └── User
│
├── exception
│   ├── DuplicateEmailException
│   ├── DuplicateUsernameException
│   ├── ErrorResponse
│   ├── GlobalExceptionHandler
│   ├── InvalidCredentialsException
│   ├── ResourceNotFoundException
│   └── ValidationErrorResponse
│
├── repository
│   ├── StudentRepository
│   └── UserRepository
│
├── security
│   ├── JwtAuthenticationFilter
│   └── JwtService
│
└── service
    ├── LoginService
    ├── StudentService
    └── UserService
📁 Project Structure
student-management-system
│
├── .mvn/
│   └── wrapper/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── dileep/
│   │   │           └── studentmanagement/
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── dileep/
│                   └── studentmanagement/
│
├── .gitattributes
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
🔐 Authentication

The application uses JWT (JSON Web Token) for authentication.

The authentication process is:

Register
   ↓
Login
   ↓
Validate username/password
   ↓
Generate JWT
   ↓
Client stores JWT
   ↓
Send JWT with protected requests
   ↓
JwtAuthenticationFilter
   ↓
Spring Security
   ↓
Access protected API
👤 User Registration
Endpoint
POST /auth/register
Example Request
{
  "username": "dileep01",
  "password": "password123"
}
Description

A new user is registered with a username and password.

The password is securely hashed using BCrypt before being stored in the database.

🔑 User Login
Endpoint
POST /auth/login
Example Request
{
  "username": "dileep01",
  "password": "password123"
}
Response

A successful login returns a JWT token.

Example:

{
  "token": "<JWT_TOKEN>"
}

The token must be sent with protected API requests.

Authorization: Bearer <JWT_TOKEN>
👥 Authorization

The application supports two roles:

USER
ADMIN
USER Permissions

A USER can:

View students
Search students
ADMIN Permissions

An ADMIN can:

View students
Search students
Create students
Update students
Delete students

Authorization is enforced at the Spring Security level.

📚 Student API Endpoints
Method	Endpoint	Description	Access
GET	/students	Get students	USER / ADMIN
GET	/students/{rollNo}	Get student by roll number	USER / ADMIN
POST	/students	Create student	ADMIN
PUT	/students/{rollNo}	Update student	ADMIN
DELETE	/students/{rollNo}	Delete student	ADMIN
GET	/students/email/{email}	Find student by email	Authenticated
GET	/students/search/name	Search by name	Authenticated
GET	/students/search/course	Search by course	Authenticated
GET	/students/search	Search by name and course	Authenticated
📌 API Examples
Register User
POST /auth/register
Content-Type: application/json

Request:

{
  "username": "dileep01",
  "password": "password123"
}
Login User
POST /auth/login
Content-Type: application/json

Request:

{
  "username": "dileep01",
  "password": "password123"
}

Response:

{
  "token": "<JWT_TOKEN>"
}
Create Student
POST /students
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

Request:

{
  "name": "Rahul Kumar",
  "email": "rahul@example.com",
  "course": "Java",
  "phone": "9876543210"
}

Response:

{
  "rollNo": 1,
  "name": "Rahul Kumar",
  "email": "rahul@example.com",
  "course": "Java",
  "phone": "9876543210"
}
Get All Students
GET /students?page=0&size=10&sortBy=name&sortDir=asc
Authorization: Bearer <JWT_TOKEN>

This request demonstrates:

Pagination
Page size
Sorting
Sort direction
Get Student by Roll Number
GET /students/{rollNo}
Authorization: Bearer <JWT_TOKEN>

Example:

GET /students/1
Authorization: Bearer <JWT_TOKEN>
Update Student
PUT /students/{rollNo}
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

Request:

{
  "name": "Rahul Kumar Updated",
  "email": "rahul@example.com",
  "course": "Spring Boot",
  "phone": "9876543210"
}
Delete Student
DELETE /students/{rollNo}
Authorization: Bearer <JWT_TOKEN>
Search by Name
GET /students/search/name?name=Rahul
Authorization: Bearer <JWT_TOKEN>
Search by Course
GET /students/search/course?course=Java
Authorization: Bearer <JWT_TOKEN>
Search by Name and Course
GET /students/search?name=Rahul&course=Java
Authorization: Bearer <JWT_TOKEN>
🔎 Search, Pagination and Sorting

The application supports multiple ways of retrieving student data.

Search by Name
GET /students/search/name?name=Rahul
Search by Course
GET /students/search/course?course=Java
Search by Name and Course
GET /students/search?name=Rahul&course=Java
Pagination

Example:

GET /students?page=0&size=10
Sorting

Example:

GET /students?sortBy=name&sortDir=asc
Pagination + Sorting

Example:

GET /students?page=0&size=10&sortBy=name&sortDir=asc
✅ Validation

The application uses Jakarta Bean Validation to validate incoming requests.

Validation includes:

Name cannot be blank
Email must have a valid format
Phone number must follow the expected format
Required request fields must be provided

Invalid requests return structured validation responses.

⚠️ Exception Handling

The application uses centralized exception handling through:

GlobalExceptionHandler

Custom exceptions include:

ResourceNotFoundException
DuplicateEmailException
DuplicateUsernameException
InvalidCredentialsException

This provides consistent error responses across the application.

🗄️ Database

The application uses:

MySQL
Spring Data JPA
Hibernate

Database:

student_management_system

Create the database using:

CREATE DATABASE student_management_system;
🔑 Environment Configuration

Sensitive configuration should never be hard-coded into the source code.

The JWT secret is configured using an environment variable:

jwt.secret=${JWT_SECRET}
jwt.expiration=3600000

Set the environment variable before running the application.

Example:

JWT_SECRET=your-secure-secret-key

Do not commit:

JWT secrets
Database passwords
.env files
Other sensitive credentials

The .gitignore file excludes .env from Git tracking.

⚙️ Database Configuration

Update the following properties according to your local MySQL configuration:

spring.datasource.url=jdbc:mysql://localhost:3306/student_management_system
spring.datasource.username=root
spring.datasource.password=YOUR_DATABASE_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
▶️ How to Run
1. Clone the Repository
git clone https://github.com/Dileep9450/student-management-system.git

Move into the project directory:

cd student-management-system
2. Create the MySQL Database

Open MySQL and execute:

CREATE DATABASE student_management_system;
3. Configure Database Credentials

Open:

src/main/resources/application.properties

Set your MySQL username and password.

Example:

spring.datasource.username=root
spring.datasource.password=YOUR_DATABASE_PASSWORD
4. Configure JWT Secret

Set the environment variable:

JWT_SECRET=your-secure-secret-key

The application reads the secret using:

jwt.secret=${JWT_SECRET}
5. Run the Application

On Windows, use the Maven Wrapper:

.\mvnw.cmd spring-boot:run

You can also run:

StudentManagementSystemApplication.java

directly from IntelliJ IDEA.

🧪 Testing

The project contains multiple levels of automated testing.

Unit Tests

Unit tests are written using:

JUnit 5
Mockito

They test service-layer functionality such as:

Saving students
Retrieving students
Updating students
Deleting students
Duplicate email handling
Resource not found handling
Controller Tests

Controller endpoints are tested using:

MockMvc

These tests verify HTTP behavior and controller responses.

Integration Tests

Integration tests verify the application working as a complete Spring Boot application with:

Spring context
Database
Security
JWT authentication
REST endpoints
Run All Tests

Execute:

.\mvnw.cmd test

The current backend test suite has been verified successfully with:

22 tests
0 failures
0 errors
📖 Swagger / OpenAPI Documentation

Swagger UI is available when the application is running.

Open:

http://localhost:8080/swagger-ui/index.html

OpenAPI specification:

http://localhost:8080/v3/api-docs

Swagger provides interactive documentation for the REST APIs.

JWT-protected endpoints can be tested using the Authorize button.

Enter:

Bearer <JWT_TOKEN>
🔒 Security

Security features implemented in this project include:

Spring Security
JWT authentication
JWT token validation
BCrypt password hashing
Stateless authentication
Role-based authorization
Protected REST endpoints
Environment-based JWT secret
Input validation
Global exception handling
📝 Logging

The application uses SLF4J for application logging.

Logging helps with:

Debugging
Monitoring application behavior
Tracking important operations
Diagnosing errors
🌳 Git Workflow

This project is maintained using Git and GitHub.

Typical workflow:

Make changes
     |
     v
git status
     |
     v
git add .
     |
     v
git commit -m "Meaningful message"
     |
     v
git push
     |
     v
GitHub

Repository:

https://github.com/Dileep9450/student-management-system
📌 Current Project Status
Backend

✅ Spring Boot REST API
✅ MySQL integration
✅ Spring Data JPA
✅ Hibernate
✅ Student CRUD
✅ DTOs
✅ Input validation
✅ Global exception handling
✅ Custom exceptions
✅ Search by name
✅ Search by course
✅ Combined search
✅ Pagination
✅ Dynamic sorting
✅ User registration
✅ User login
✅ BCrypt password hashing
✅ JWT authentication
✅ JWT validation
✅ USER / ADMIN authorization
✅ Swagger/OpenAPI
✅ SLF4J logging
✅ Unit testing
✅ Mockito testing
✅ Controller testing
✅ MockMvc testing
✅ Integration testing
✅ Git version control
✅ GitHub repository

Frontend

🚧 Planned

A React-based frontend will be added in the next phase.

Planned frontend features include:

Login page
Registration page
Dashboard
Student list
Student search
Add student
Update student
Delete student
JWT token handling
Role-based UI
Responsive design
🚀 Future Enhancements

Planned improvements include:

React frontend
Responsive user interface
Dashboard statistics
Better UI/UX
Frontend authentication
Frontend role-based access
Backend deployment
Frontend deployment
Production database
CI/CD pipeline
Docker support
🧩 Future Full-Stack Architecture

After frontend development, the complete application will follow this architecture:

                React Frontend
                      |
                      | REST API
                      v
              Spring Boot Backend
                      |
              Spring Security
                      |
                     JWT
                      |
                      v
               Spring Data JPA
                      |
                      v
                 MySQL Database
👨‍💻 Author

Dileep Papaganti

GitHub:

https://github.com/Dileep9450

Project Repository:

https://github.com/Dileep9450/student-management-system

📄 License

This project is currently developed for educational and portfolio purposes.

⭐ Project Goal

The goal of this project is to build a production-style backend application while demonstrating practical knowledge of:

Java
Spring Boot
REST API development
Spring Security
JWT authentication
Database management
JPA/Hibernate
Software testing
Git and GitHub
Clean backend architecture

The next stage is to extend this secure backend with a modern React frontend and deploy the complete full-stack application.











# Student Management System

A secure RESTful Student Management System built using **Java, Spring Boot, Spring Data JPA, Hibernate, MySQL, Spring Security, and JWT**.

The application provides APIs for managing student records with authentication, role-based authorization, validation, exception handling, searching, pagination, sorting, Swagger documentation, logging, and automated testing.

---

## 📌 Project Overview

The Student Management System is designed as a backend REST API that provides secure and reliable student record management.

The application follows a layered architecture:

```text
Client
   |
   v
REST API
   |
   v
Controller Layer
   |
   v
Service Layer
   |
   v
Repository Layer
   |
   v
MySQL Database

Authentication and authorization are handled using Spring Security and JWT.

Client
   |
   | Login
   v
AuthController
   |
   v
LoginService
   |
   v
JWT Token
   |
   v
Client
   |
   | Authorization: Bearer <JWT_TOKEN>
   v
JWT Authentication Filter
   |
   v
Spring Security
   |
   v
Protected REST APIs
🚀 Features
Student Management
Create student
Get all students
Get student by roll number
Update student
Delete student
Find student by email
Search
Search students by name
Search students by course
Search students by name and course
Pagination and Sorting
Pagination support
Dynamic sorting
Sort by different student fields
Ascending and descending sorting
Authentication
User registration
User login
JWT-based authentication
JWT token validation
Stateless authentication
BCrypt password hashing
Authorization
USER role
ADMIN role
Role-based access to student APIs
Validation
Request validation using Jakarta Bean Validation
Required field validation
Email format validation
Phone number validation
Exception Handling
Global exception handling
Custom exceptions
Structured error responses
Duplicate email handling
Duplicate username handling
Invalid credentials handling
Resource not found handling
Development and Testing
Swagger/OpenAPI documentation
SLF4J logging
Unit testing
Mockito
JUnit 5
MockMvc controller testing
Integration testing
🛠️ Technologies Used
Backend
Java 25
Spring Boot 3.5.5
Spring Web
Spring Data JPA
Hibernate
Spring Security
JWT
BCrypt
MySQL
Maven
Lombok
Testing
JUnit 5
Mockito
MockMvc
Spring Boot Test
API Documentation
Swagger
OpenAPI
Version Control
Git
GitHub
🏗️ Architecture

The project follows a layered architecture.

com.dileep.studentmanagement
│
├── config
│   ├── OpenAPIConfig
│   └── SecurityConfig
│
├── controller
│   ├── AuthController
│   └── StudentController
│
├── dto
│   ├── LoginRequest
│   ├── LoginResponse
│   ├── RegisterRequest
│   ├── RegisterResponse
│   ├── StudentRequest
│   └── StudentResponse
│
├── entity
│   ├── Student
│   └── User
│
├── exception
│   ├── DuplicateEmailException
│   ├── DuplicateUsernameException
│   ├── ErrorResponse
│   ├── GlobalExceptionHandler
│   ├── InvalidCredentialsException
│   ├── ResourceNotFoundException
│   └── ValidationErrorResponse
│
├── repository
│   ├── StudentRepository
│   └── UserRepository
│
├── security
│   ├── JwtAuthenticationFilter
│   └── JwtService
│
└── service
    ├── LoginService
    ├── StudentService
    └── UserService
📁 Project Structure
student-management-system
│
├── .mvn/
│   └── wrapper/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── dileep/
│   │   │           └── studentmanagement/
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── dileep/
│                   └── studentmanagement/
│
├── .gitattributes
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
🔐 Authentication

The application uses JWT (JSON Web Token) for authentication.

The authentication process is:

Register
   ↓
Login
   ↓
Validate username/password
   ↓
Generate JWT
   ↓
Client stores JWT
   ↓
Send JWT with protected requests
   ↓
JwtAuthenticationFilter
   ↓
Spring Security
   ↓
Access protected API
👤 User Registration
Endpoint
POST /auth/register
Example Request
{
  "username": "dileep01",
  "password": "password123"
}
Description

A new user is registered with a username and password.

The password is securely hashed using BCrypt before being stored in the database.

🔑 User Login
Endpoint
POST /auth/login
Example Request
{
  "username": "dileep01",
  "password": "password123"
}
Response

A successful login returns a JWT token.

Example:

{
  "token": "<JWT_TOKEN>"
}

The token must be sent with protected API requests.

Authorization: Bearer <JWT_TOKEN>
👥 Authorization

The application supports two roles:

USER
ADMIN
USER Permissions

A USER can:

View students
Search students
ADMIN Permissions

An ADMIN can:

View students
Search students
Create students
Update students
Delete students

Authorization is enforced at the Spring Security level.

📚 Student API Endpoints
Method	Endpoint	Description	Access
GET	/students	Get students	USER / ADMIN
GET	/students/{rollNo}	Get student by roll number	USER / ADMIN
POST	/students	Create student	ADMIN
PUT	/students/{rollNo}	Update student	ADMIN
DELETE	/students/{rollNo}	Delete student	ADMIN
GET	/students/email/{email}	Find student by email	Authenticated
GET	/students/search/name	Search by name	Authenticated
GET	/students/search/course	Search by course	Authenticated
GET	/students/search	Search by name and course	Authenticated
📌 API Examples
Register User
POST /auth/register
Content-Type: application/json

Request:

{
  "username": "dileep01",
  "password": "password123"
}
Login User
POST /auth/login
Content-Type: application/json

Request:

{
  "username": "dileep01",
  "password": "password123"
}

Response:

{
  "token": "<JWT_TOKEN>"
}
Create Student
POST /students
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

Request:

{
  "name": "Rahul Kumar",
  "email": "rahul@example.com",
  "course": "Java",
  "phone": "9876543210"
}

Response:

{
  "rollNo": 1,
  "name": "Rahul Kumar",
  "email": "rahul@example.com",
  "course": "Java",
  "phone": "9876543210"
}
Get All Students
GET /students?page=0&size=10&sortBy=name&sortDir=asc
Authorization: Bearer <JWT_TOKEN>

This request demonstrates:

Pagination
Page size
Sorting
Sort direction
Get Student by Roll Number
GET /students/{rollNo}
Authorization: Bearer <JWT_TOKEN>

Example:

GET /students/1
Authorization: Bearer <JWT_TOKEN>
Update Student
PUT /students/{rollNo}
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

Request:

{
  "name": "Rahul Kumar Updated",
  "email": "rahul@example.com",
  "course": "Spring Boot",
  "phone": "9876543210"
}
Delete Student
DELETE /students/{rollNo}
Authorization: Bearer <JWT_TOKEN>
Search by Name
GET /students/search/name?name=Rahul
Authorization: Bearer <JWT_TOKEN>
Search by Course
GET /students/search/course?course=Java
Authorization: Bearer <JWT_TOKEN>
Search by Name and Course
GET /students/search?name=Rahul&course=Java
Authorization: Bearer <JWT_TOKEN>
🔎 Search, Pagination and Sorting

The application supports multiple ways of retrieving student data.

Search by Name
GET /students/search/name?name=Rahul
Search by Course
GET /students/search/course?course=Java
Search by Name and Course
GET /students/search?name=Rahul&course=Java
Pagination

Example:

GET /students?page=0&size=10
Sorting

Example:

GET /students?sortBy=name&sortDir=asc
Pagination + Sorting

Example:

GET /students?page=0&size=10&sortBy=name&sortDir=asc
✅ Validation

The application uses Jakarta Bean Validation to validate incoming requests.

Validation includes:

Name cannot be blank
Email must have a valid format
Phone number must follow the expected format
Required request fields must be provided

Invalid requests return structured validation responses.

⚠️ Exception Handling

The application uses centralized exception handling through:

GlobalExceptionHandler

Custom exceptions include:

ResourceNotFoundException
DuplicateEmailException
DuplicateUsernameException
InvalidCredentialsException

This provides consistent error responses across the application.

🗄️ Database

The application uses:

MySQL
Spring Data JPA
Hibernate

Database:

student_management_system

Create the database using:

CREATE DATABASE student_management_system;
🔑 Environment Configuration

Sensitive configuration should never be hard-coded into the source code.

The JWT secret is configured using an environment variable:

jwt.secret=${JWT_SECRET}
jwt.expiration=3600000

Set the environment variable before running the application.

Example:

JWT_SECRET=your-secure-secret-key

Do not commit:

JWT secrets
Database passwords
.env files
Other sensitive credentials

The .gitignore file excludes .env from Git tracking.

⚙️ Database Configuration

Update the following properties according to your local MySQL configuration:

spring.datasource.url=jdbc:mysql://localhost:3306/student_management_system
spring.datasource.username=root
spring.datasource.password=YOUR_DATABASE_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
▶️ How to Run
1. Clone the Repository
git clone https://github.com/Dileep9450/student-management-system.git

Move into the project directory:

cd student-management-system
2. Create the MySQL Database

Open MySQL and execute:

CREATE DATABASE student_management_system;
3. Configure Database Credentials

Open:

src/main/resources/application.properties

Set your MySQL username and password.

Example:

spring.datasource.username=root
spring.datasource.password=YOUR_DATABASE_PASSWORD
4. Configure JWT Secret

Set the environment variable:

JWT_SECRET=your-secure-secret-key

The application reads the secret using:

jwt.secret=${JWT_SECRET}
5. Run the Application

On Windows, use the Maven Wrapper:

.\mvnw.cmd spring-boot:run

You can also run:

StudentManagementSystemApplication.java

directly from IntelliJ IDEA.

🧪 Testing

The project contains multiple levels of automated testing.

Unit Tests

Unit tests are written using:

JUnit 5
Mockito

They test service-layer functionality such as:

Saving students
Retrieving students
Updating students
Deleting students
Duplicate email handling
Resource not found handling
Controller Tests

Controller endpoints are tested using:

MockMvc

These tests verify HTTP behavior and controller responses.

Integration Tests

Integration tests verify the application working as a complete Spring Boot application with:

Spring context
Database
Security
JWT authentication
REST endpoints
Run All Tests

Execute:

.\mvnw.cmd test

The current backend test suite has been verified successfully with:

22 tests
0 failures
0 errors
📖 Swagger / OpenAPI Documentation

Swagger UI is available when the application is running.

Open:

http://localhost:8080/swagger-ui/index.html

OpenAPI specification:

http://localhost:8080/v3/api-docs

Swagger provides interactive documentation for the REST APIs.

JWT-protected endpoints can be tested using the Authorize button.

Enter:

Bearer <JWT_TOKEN>
🔒 Security

Security features implemented in this project include:

Spring Security
JWT authentication
JWT token validation
BCrypt password hashing
Stateless authentication
Role-based authorization
Protected REST endpoints
Environment-based JWT secret
Input validation
Global exception handling
📝 Logging

The application uses SLF4J for application logging.

Logging helps with:

Debugging
Monitoring application behavior
Tracking important operations
Diagnosing errors
🌳 Git Workflow

This project is maintained using Git and GitHub.

Typical workflow:

Make changes
     |
     v
git status
     |
     v
git add .
     |
     v
git commit -m "Meaningful message"
     |
     v
git push
     |
     v
GitHub

Repository:

https://github.com/Dileep9450/student-management-system
📌 Current Project Status
Backend

✅ Spring Boot REST API
✅ MySQL integration
✅ Spring Data JPA
✅ Hibernate
✅ Student CRUD
✅ DTOs
✅ Input validation
✅ Global exception handling
✅ Custom exceptions
✅ Search by name
✅ Search by course
✅ Combined search
✅ Pagination
✅ Dynamic sorting
✅ User registration
✅ User login
✅ BCrypt password hashing
✅ JWT authentication
✅ JWT validation
✅ USER / ADMIN authorization
✅ Swagger/OpenAPI
✅ SLF4J logging
✅ Unit testing
✅ Mockito testing
✅ Controller testing
✅ MockMvc testing
✅ Integration testing
✅ Git version control
✅ GitHub repository

Frontend

🚧 Planned

A React-based frontend will be added in the next phase.

Planned frontend features include:

Login page
Registration page
Dashboard
Student list
Student search
Add student
Update student
Delete student
JWT token handling
Role-based UI
Responsive design
🚀 Future Enhancements

Planned improvements include:

React frontend
Responsive user interface
Dashboard statistics
Better UI/UX
Frontend authentication
Frontend role-based access
Backend deployment
Frontend deployment
Production database
CI/CD pipeline
Docker support
🧩 Future Full-Stack Architecture

After frontend development, the complete application will follow this architecture:

                React Frontend
                      |
                      | REST API
                      v
              Spring Boot Backend
                      |
              Spring Security
                      |
                     JWT
                      |
                      v
               Spring Data JPA
                      |
                      v
                 MySQL Database
👨‍💻 Author

Dileep Papaganti

GitHub:

https://github.com/Dileep9450

Project Repository:

https://github.com/Dileep9450/student-management-system

📄 License

This project is currently developed for educational and portfolio purposes.

⭐ Project Goal

The goal of this project is to build a production-style backend application while demonstrating practical knowledge of:

Java
Spring Boot
REST API development
Spring Security
JWT authentication
Database management
JPA/Hibernate
Software testing
Git and GitHub
Clean backend architecture

The next stage is to extend this secure backend with a modern React frontend and deploy the complete full-stack application.