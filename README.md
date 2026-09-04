# Student Management System

A RESTful Student Management System built using **Spring Boot, Spring Data JPA, MySQL, Spring Security, and JWT authentication**.

This project provides secure APIs for managing student records with validation, exception handling, pagination, sorting, searching, role-based authorization, and automated testing.

---

## 🚀 Features

- Student CRUD operations
- User registration and login
- JWT-based authentication
- Role-based authorization using USER and ADMIN roles
- BCrypt password hashing
- Input validation
- Global exception handling
- Duplicate email detection
- Duplicate username detection
- Search students by name
- Search students by course
- Search students by name and course
- Pagination
- Dynamic sorting
- Swagger/OpenAPI documentation
- SLF4J logging
- Unit testing with JUnit 5 and Mockito
- Controller testing with MockMvc
- Integration testing
- MySQL database integration

---

## 🛠️ Technologies Used

### Backend

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Spring Security
- JWT
- BCrypt
- MySQL
- Maven
- Lombok

### Testing

- JUnit 5
- Mockito
- MockMvc
- Spring Boot Test

### API Documentation

- Swagger / OpenAPI

---

## 🏗️ Project Architecture

```text
Client
   |
   v
REST API
   |
   v
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
MySQL Database