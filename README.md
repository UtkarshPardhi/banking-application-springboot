# Banking Application - Spring Boot

A RESTful Banking Application built using Spring Boot, Spring Data JPA, and PostgreSQL.
This project supports account creation, balance management, deposits, withdrawals,
and proper exception handling.

--------------------------------------------------

FEATURES

Account Management
- Create bank account
- Get account details by ID
- Get all accounts
- Delete account

Transaction Operations
- Deposit money into account
- Withdraw money from account
- Prevent withdrawal when balance is insufficient

Exception Handling
- Custom exceptions for Account Not Found
- Global exception handling using ControllerAdvice
- Proper HTTP status codes and error responses

--------------------------------------------------

PROJECT STRUCTURE

src/main/java/com/example/banking_app
|-- controller
|-- service
|   |-- impl
|-- repository
|-- entity
|-- dto
|-- mapper
|-- exception
|-- BankingAppApplication.java

--------------------------------------------------

TECH STACK

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- IntelliJ IDEA
- Postman

--------------------------------------------------

API ENDPOINTS

Accounts
POST    /api/accounts
GET     /api/accounts
GET     /api/accounts/{id}
DELETE  /api/accounts/{id}

Transactions
POST    /api/accounts/{id}/deposit
POST    /api/accounts/{id}/withdraw

--------------------------------------------------

DATABASE CONFIGURATION

spring.datasource.url=jdbc:postgresql://localhost:5432/banking_app
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

--------------------------------------------------

HOW TO RUN

git clone https://github.com/UtkarshPardhi/Banking-App.git
cd Banking-App
mvn spring-boot:run

Application runs on:
http://localhost:8080

--------------------------------------------------

AUTHOR

Utkarsh Pardhi
Java Backend Developer
GitHub: https://github.com/UtkarshPardhi
