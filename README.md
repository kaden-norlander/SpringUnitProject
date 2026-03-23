# Junction - Project Management System 🚂

Junction is a robust, railroad-themed project and task management web application built with Spring Boot. It allows users to act as "Operators" to manage their "Freight Yard" (dashboard), deploy "Lines" (projects), hitch "Cars" (sections), and load "Cargo" (tasks). 

## 🛠️ Tech Stack

* **Backend:** Java 25, Spring Boot 4.0.3
* **Database:** PostgreSQL, Spring Data JPA (Hibernate)
* **Security:** Spring Security 6 (BCrypt Password Encoding, Form-based Login)
* **Frontend:** Thymeleaf Templates, Tailwind CSS (via CDN)
* **Build Tool:** Maven

## ✨ Features

* **Operator Authentication:** Secure user registration, login, and profile management (update/delete).
* **Project (Line) Management:** Create, view, search, and dismantle projects. Features dynamic progress bars based on task completion.
* **Section (Car) Management:** Group tasks logically by adding sections to your projects.
* **Task (Cargo) Tracking:** Add, toggle completion, and delete tasks. Tasks support delivery dates, descriptions, and handling classes (STANDARD, EXPRESS, HAZMAT).
* **Themed UI:** fully responsive industrial/railroad-themed user interface styled with Tailwind CSS.

## 🗄️ Data Model

The application follows a standard relational hierarchy:
1.  **User (Operator):** Can own multiple Projects.
2.  **Project (Line):** Belongs to a User, contains multiple Sections.
3.  **Section (Car):** Belongs to a Project, contains multiple Tasks.
4.  **Task (Cargo):** Belongs to a Section, holds details like `cargoClass`, `deliveryDate`, and `isCompleted` status.

## 🚀 Getting Started

### Prerequisites
* Java Development Kit (JDK) 25
* PostgreSQL installed and running
* Maven (or use the provided `mvnw` wrapper)

### Configuration

1. Clone the repository.
2. Open `src/main/resources/application.properties` and configure your PostgreSQL database credentials:

```properties
spring.application.name=SpringUnitProject
spring.datasource.url=jdbc:postgresql://localhost:your_port/your_db_name
spring.datasource.username=your_username
spring.datasource.password=your_password

# Hibernate properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

Running the Application

# On Windows
mvnw.cmd spring-boot:run

# On macOS/Linux
./mvnw spring-boot:run

Once the application is running, open your web browser and navigate to:
http://localhost:8080/login
