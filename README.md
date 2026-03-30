# 🏦 Banking Application – Spring Boot Backend

A production-ready **RESTful Banking Application** built using **Spring Boot, Spring Data JPA, PostgreSQL, and JWT Authentication**.
The application supports secure account management, transactions, role-based access control, and API documentation using Swagger UI.
A React-based frontend has also been partially integrated.

---

## 🚀 Features

### 🔐 Authentication & Security

* User Login with JWT Authentication
* Secure REST APIs using Spring Security
* Token-based Authorization system

### 👥 Role-Based Access Control

* Separate access for **USER** and **ADMIN**
* Protected endpoints based on roles
* Example endpoints:

  * `/api/user/test`
  * `/api/admin/test`

### 🧾 Account Management

* Create bank account
* Get account details by ID
* Get all accounts
* Delete account

### 💳 Transaction Operations

* Deposit money into account
* Withdraw money from account
* Prevent withdrawal when balance is insufficient
* Maintain transaction history per account

### ⚠️ Exception Handling

* Custom exceptions (Account, Transaction, User)
* Global exception handling using `@ControllerAdvice`
* Structured error responses with proper HTTP status codes

Backend APIs tested using Swagger UI and Postman.

---

## 🌐 Frontend

A React-based frontend has been partially integrated with the backend APIs.
Further UI enhancements and feature completion are currently in progress.
All backend functionalities are fully implemented and can be tested independently via Swagger UI.

---

## 📄 API Documentation (Swagger UI)

Interactive API documentation is available via Swagger UI:

👉 http://localhost:8080/swagger-ui/index.html

You can:

* Test all APIs directly from the browser
* Send requests & view responses
* Authorize using JWT token

### 🔑 Authorization Steps:

1. Login using `/api/auth/login`
2. Copy the JWT token from response
3. Click **Authorize** in Swagger
4. Enter:

```
Bearer <your_jwt_token>
```

⚠️ Note:

* Some endpoints are **role-restricted (USER / ADMIN)**
* Use appropriate token based on role

---

## ⭐ Project Highlights

* Implemented secure authentication using JWT
* Designed layered architecture (Controller → Service → Repository)
* Implemented Role-Based Access Control (RBAC)
* Used DTO & Mapper pattern for clean data handling
* Integrated Swagger UI for API testing
* Implemented global exception handling for robust error responses

---

## 🏗 Project Architecture

```
src/main/java/com/example/Banking_App
│
├── controller
├── service
│   └── impl
├── repository
├── entity
├── dto
├── mapper
├── exception
├── auth
├── config
├── util
└── BankingAppApplication.java
```

---

## 🧱 Tech Stack

* Java 21
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* Swagger UI
* React (Frontend - In Progress)

---

## 📌 API Endpoints

### 🔐 Auth

| Method | Endpoint          | Description |
| ------ | ----------------- | ----------- |
| POST   | `/api/auth/login` | Login user  |

---

### 🧾 Accounts

| Method | Endpoint             | Description       |
| ------ | -------------------- | ----------------- |
| POST   | `/api/accounts`      | Create account    |
| GET    | `/api/accounts`      | Get all accounts  |
| GET    | `/api/accounts/{id}` | Get account by ID |
| DELETE | `/api/accounts/{id}` | Delete account    |

---

### 💳 Transactions

| Method | Endpoint                          | Description             |
| ------ | --------------------------------- | ----------------------- |
| PUT    | `/api/accounts/{id}/deposit`      | Deposit money           |
| PUT    | `/api/accounts/{id}/withdraw`     | Withdraw money          |
| GET    | `/api/accounts/{id}/transactions` | Get transaction history |

---

### 🧪 Role-Based Test APIs

| Method | Endpoint          | Access |
| ------ | ----------------- | ------ |
| GET    | `/api/user/test`  | USER   |
| GET    | `/api/admin/test` | ADMIN  |

---

## 🗄 Database Configuration

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/banking_app
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## ⚙️ How To Run

```bash
git clone https://github.com/UtkarshPardhi/banking-application-springboot.git
cd banking-application-springboot
mvn spring-boot:run
```

Application runs on:

```
http://localhost:8080
```

---

## 🔮 Future Improvements

* Complete frontend integration
* Role management UI
* Pagination & Sorting
* Cloud Deployment (Render / AWS)
* Docker Containerization

---

## 👨‍💻 Author

**Utkarsh Pardhi**
Java Backend Developer
GitHub: https://github.com/UtkarshPardhi
