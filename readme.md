# 🧠 Habit Tracker Backend API

[![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)](https://www.mysql.com/)
[![JWT](https://img.shields.io/badge/Authentication-JWT-black?logo=jsonwebtokens)](https://jwt.io/)

A robust Spring Boot REST API providing the backbone for the Habit Tracker application. This service handles secure authentication, habit lifecycle management, and administrative oversight.

---

## 🛠 Tech Stack

- **Backend:** Java 17, Spring Boot
- **Security:** Spring Security, JWT (JSON Web Tokens)
- **Data:** Spring Data JPA, Hibernate, MySQL
- **Build Tool:** Maven
---

## ▶️ Run the Project

Follow these steps to set up and run the backend locally on your machine.

### 1. Clone the Repository
Open your terminal and run the following command to clone the project:
```bash
git clone https://github.com/abdellahchatioui/Habit-Tracker-backend.git

cd Habit-Tracker-backend

```

### 2. Database Setup
Ensure **MySQL Server** is running. You must create the schema manually before the first run:
```sql
CREATE DATABASE habit_tracker;
```

---
## 🏗 Architecture & Design

### Project Structure
The project follows a standard layered architecture to ensure separation of concerns:

```text
com.habittracker
├── config      # Configuration classes (CORS, Beans)
├── controller  # REST Endpoints
├── service     # Business Logic
├── repository  # Data Access Layer (JPA)
├── model       # Database Entities
├── dto         # Data Transfer Objects
└── security    # JWT Filters & Auth Providers
```
# 🧠 Habit Tracker Backend API

A robust **Spring Boot REST API** designed for the Habit Tracker ecosystem. This backend handles secure user onboarding, habit lifecycle management, and administrative control using JWT-based security.

---

## 🔐 Authentication & Security

The API implements **JWT (JSON Web Token)** for stateless, secure communication. 

1. **Login**: Authenticate via the login endpoint to receive a token.
2. **Access**: Include the token in the **Authorization** header for all protected requests:
   `Authorization: Bearer <your_token>`

### 👥 Role-Based Access Control (RBAC)
* **`USER`**: Standard access to manage personal habits and logs.
* **`ADMIN`**: Elevated access to manage user accounts and system status.

---

## 📌 API Endpoints

### 🔑 Auth (Public)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register a new account |
| `POST` | `/api/auth/login` | Authenticate and receive JWT |

### 📘 Habits (`USER` Role)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/habits` | Retrieve all habits for the logged-in user |
| `POST` | `/api/habits` | Create a new habit |
| `PUT` | `/api/habits/{id}` | Update an existing habit |
| `DELETE` | `/api/habits/{id}` | Delete a habit |
| `POST` | `/api/habits/{id}/log` | Mark a habit as completed (Log entry) |

### 👑 Admin Oversight (`ADMIN` Role)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/admin/users` | View all registered users |
| `PUT` | `/api/admin/users/{id}/block` | Block/Disable a specific user |
| `DELETE` | `/api/admin/users/{id}` | Permanently delete a user account |

---


## 👨‍💻 Author

**Abdellah Chatioui** *Fullstack Developer*

[![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/abdellahchatioui)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/abdellah-chatioui-5b9426299/)


