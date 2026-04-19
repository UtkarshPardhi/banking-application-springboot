# 🏦 FinGo – Banking Application (Spring Boot Backend)

A production-ready RESTful Banking Application built using Spring Boot, Spring Security, PostgreSQL, and JWT Authentication.  
This backend powers the FinGo banking system, enabling secure account management, transactions, analytics, and role-based access control.

---

## 🌐 Live Deployment

- 🔧 Backend (Render): https://banking-application-springboot.onrender.com
- 🚀 Frontend (Vercel): https://banking-app-frontend-three.vercel.app/

---

## 🚀 Features

### 🔐 Authentication & Security
- JWT-based authentication system
- Secure REST APIs using Spring Security
- Token-based authorization
- Password encryption using BCrypt

---

### 👥 Role-Based Access Control
- Separate roles: USER and ADMIN
- Protected endpoints based on roles

Example:
- /api/user/test → USER access
- /api/admin/test → ADMIN access

---

### 🧾 Account Management
- Create bank account
- Get account by ID
- Get all accounts
- Delete account

---

### 💳 Transaction Operations
- Deposit money
- Withdraw money
- Prevent withdrawal if balance is insufficient
- Maintain transaction history per account

---

### 📊 Analytics & Reports
- Account-based statistics
- Transaction summaries (credit/debit)
- Export reports:
  - Excel
  - CSV
  - PDF

---

### ⚠️ Exception Handling
- Custom exceptions (Account, Transaction, User)
- Global exception handling using @ControllerAdvice
- Structured error responses with proper HTTP status codes

---

## 📄 API Documentation (Swagger UI)

Swagger UI is available here:

https://banking-application-springboot.onrender.com/swagger-ui/index.html

You can:
- Test APIs directly
- Send requests & view responses
- Authorize using JWT token

---

## 🔑 Authorization Steps

1. Login using:
POST /api/auth/login

2. Copy JWT token from response

3. Click "Authorize" in Swagger

4. Enter:
Bearer <your_token>

---

## 🏗 Project Architecture

src/main/java/com/example/Banking_App

controller  
service  
 └── impl  
repository  
entity  
dto  
mapper  
exception  
auth  
config  
util  
BankingAppApplication.java  

Architecture Pattern:
- Layered Architecture (Controller → Service → Repository)
- DTO & Mapper pattern
- Clean separation of concerns

---

## 🧱 Tech Stack

- Java 21
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Swagger UI

---

## 📌 API Endpoints

### 🔐 Auth
POST /api/auth/login → Login user

---

### 🧾 Accounts
POST /api/accounts → Create account  
GET /api/accounts → Get all accounts  
GET /api/accounts/{id} → Get account by ID  
DELETE /api/accounts/{id} → Delete account  

---

### 💳 Transactions
PUT /api/accounts/{id}/deposit → Deposit money  
PUT /api/accounts/{id}/withdraw → Withdraw money  
GET /api/accounts/{id}/transactions → Transaction history  

---

### 🧪 Role-Based Test APIs
GET /api/user/test → USER  
GET /api/admin/test → ADMIN  

---

## 🗄 Database Configuration

spring.datasource.url=jdbc:postgresql://localhost:5432/banking_app  
spring.datasource.username=postgres  
spring.datasource.password=your_password  

spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true  

---

## ⚙️ How To Run Locally

git clone https://github.com/UtkarshPardhi/banking-application-springboot.git  
cd banking-application-springboot  
mvn spring-boot:run  

Application runs on:  
http://localhost:8080  

---

## 🔮 Future Improvements

- Refresh token implementation
- Role management UI
- Pagination & sorting
- Docker containerization
- CI/CD pipeline
- Cloud scaling (AWS)

---

## 👨‍💻 Author

Utkarsh Pardhi  
Full Stack Developer  

- 💼 LinkedIn: https://www.linkedin.com/in/utkarsh-pardhi-26b8a0257  
- 💻 GitHub: https://github.com/UtkarshPardhi  

---

⭐ If you like this project, give it a star on GitHub!
