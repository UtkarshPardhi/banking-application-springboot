# 🏦 Banking Application – Spring Boot Backend

A RESTful Banking Application built using **Spring Boot, Spring Data JPA, and PostgreSQL**.  
This project supports account creation, balance management, deposits, withdrawals, transaction tracking, and structured exception handling.

---

## 🚀 Features

### 🧾 Account Management
- Create bank account
- Get account details by ID
- Get all accounts
- Delete account

### 💳 Transaction Operations
- Deposit money into account
- Withdraw money from account
- Prevent withdrawal when balance is insufficient
- Maintain transaction history per account

### ⚠️ Exception Handling
- Custom exception for Account Not Found
- Global exception handling using `@ControllerAdvice`
- Proper HTTP status codes and structured error responses

---

## 🏗 Project Architecture

```
src/main/java/com/example/banking_app
│
├── controller
├── service
│   └── impl
├── repository
├── entity
├── dto
├── mapper
├── exception
└── BankingAppApplication.java
```

Architecture Pattern Used:
- DTO Layer
- Mapper Layer
- Service Layer
- Repository Layer
- Global Exception Handling

---

## 🧱 Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- IntelliJ IDEA
- Postman

---

## 📌 API Endpoints

### Accounts

| Method | Endpoint | Description |
|--------|----------|------------|
| POST | `/api/accounts` | Create account |
| GET | `/api/accounts` | Get all accounts |
| GET | `/api/accounts/{id}` | Get account by ID |
| DELETE | `/api/accounts/{id}` | Delete account |

### Transactions

| Method | Endpoint | Description |
|--------|----------|------------|
| PUT | `/api/accounts/{id}/deposit` | Deposit money |
| PUT | `/api/accounts/{id}/withdraw` | Withdraw money |
| GET | `/api/accounts/{id}/transactions` | Get transaction history |

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

- JWT-based Authentication
- Role-based access (Admin/User)
- Pagination & Sorting
- Cloud Deployment
- Docker Support

---

## 👨‍💻 Author

**Utkarsh Pardhi**  
Java Backend Developer  
GitHub: https://github.com/UtkarshPardhi
