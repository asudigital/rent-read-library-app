POST Man collection :-> 
https://ss5555-6518.postman.co/workspace/ss-Workspace~ce2c340b-462f-4847-809b-f944b2818537/collection/28031213-1fa7e97e-20cd-4733-91bb-06aa9db7fe34?action=share&creator=28031213


# 📚 RentRead - Book Rental Management System

RentRead is a RESTful web application built using **Spring Boot** that allows users to **rent books**, manage book inventory, and register/login with role-based access. The system supports different user roles, like **Admin** and **User**, with proper access control and custom exception handling.

---

## 🚀 Features

- 🔐 **Authentication & Authorization**
  - User signup and login with Basic Authentication
  - Role-based access control using Spring Security
  - Custom access denied messages for unauthorized actions

- 📚 **Book Management**
  - Admin can create, update, and delete books
  - Users can view available books
  - Book availability status handled via an `enum`

- 👥 **User Management**
  - Register new users
  - Admin and User roles with different permissions

- ⚠️ **Exception Handling**
  - Custom error responses for forbidden access and internal errors
  - Consistent error structure using custom handler

---

## 🛠️ Technologies Used

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- Lombok
- Jackson
- Postman (for testing)

---

## 📂 Project Structure

com.crio.rent_read
│
├── config # Security configuration and handlers
│ ├── SecurityConfig.java
│ └── CustomAccessDeniedHandler.java
│
├── controller # REST API endpoints
│
├── model # Entity classes like Book, Rental, User
│
├── repository # JPA Repositories
│
├── service # Business logic for book and user
│
├── dto # DTOs if used for data transfer (optional)
│
└── exception # Custom exceptions and handlers

yaml
Copy
Edit

---

## 🧪 Sample Request

**PUT /books/{id}** – Update book info (admin only)

```json
{
  "id": 17,
  "title": "Test Book 1",
  "author": "Test Author 123",
  "genre": "FICTION",
  "availabilityStatus": "AVAILABLE"
}
Response if user is not admin:

json
Copy
Edit
{
  "message": "Access Denied: You don't have permission to perform this action",
  "httpStatus": "FORBIDDEN",
  "localDateTime": "2025-03-10T20:51:27.8317066"
}
🗄️ Database Setup
Use the following MySQL commands to reset the schema:

sql
Copy
Edit
DROP DATABASE IF EXISTS test_db;
CREATE DATABASE test_db;
CREATE USER 'assessment'@'localhost' IDENTIFIED BY 'redrum';
GRANT ALL PRIVILEGES ON *.* TO 'assessment'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
🧑‍💻 Default Users
Username	Password	Role
admin@rentread2.com	admin12345	ADMIN
asutoshmunu321@gmail.com	user123	USER

🧰 Running the Application
Clone the repository:

bash
Copy
Edit
git clone <your-repo-url>
cd rent-read
Configure application.properties or application.yml for MySQL.

Run using Maven or your IDE:

bash
Copy
Edit
./mvnw spring-boot:run
