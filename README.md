# 🏋️ Gym Management System

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Maven](https://img.shields.io/badge/Maven-Build-blue?style=flat-square&logo=apache-maven)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-336791?style=flat-square&logo=postgresql)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)
![Version](https://img.shields.io/badge/Version-1.0-blue?style=flat-square)

> A comprehensive JavaFX-based console application for managing gym memberships, classes, trainers, and merchandise. Built as a collaborative student project for Keyin College's Advanced Java course.

---

## 📋 Project Information

**Institution:** Keyin College  
**Course:** Advanced Java (Semester 3)  
**Project Type:** Sprint Final Capstone

### 👥 Team Members

- **Keith Bishop** - Database Architecture, Data Layer & UI Design/Implementation
- **Matt Reid** - Project Management & Documentation
- **Charles Rubia** - Business Logic & Reporting

---

## ✨ Features

### 👤 User Management

- **Authentication** - Secure login/registration system with role-based access
- **Multiple Roles** - Support for Members, Trainers, and Admins
- **User Profiles** - Comprehensive user information storage and management

### 🏃 Membership Management

- **Membership Types** - Standard, Premium, and Student tiers
- **Active Status Tracking** - Monitor member status and activity
- **Enrollment Management** - Easy member onboarding and lifecycle

### 📚 Class Management

- **Class Scheduling** - Create, edit, and manage gym classes
- **Enrollment Tracking** - Real-time enrollment and capacity monitoring
- **Class Details** - Schedule, capacity, and enrollment data

### 👨‍🏫 Trainer Management

- **Trainer Profiles** - Specialty areas and experience tracking
- **Reporting** - View and analyze trainer statistics
- **Member Association** - Link trainers to classes and members

### 🛍️ Merchandise Shop

- **Product Catalog** - Manage gym merchandise inventory
- **Categories** - Organize products (Accessories, Equipment, Apparel)
- **Stock Management** - Track inventory levels and pricing
- **Purchase Tracking** - Member purchase history and transactions

### 📊 Reporting & Analytics

- **Member Reports** - Membership status summaries
- **Class Reports** - Enrollment and occupancy metrics
- **Trainer Statistics** - Performance and experience data
- **Inventory Reports** - Merchandise stock and valuation

### 🛠️ Admin Functions

- **Database Management** - Database status and health monitoring
- **System Maintenance** - System information and configuration
- **User Administration** - Create and manage users across roles

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    GYM MANAGEMENT SYSTEM                    │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌──────────────────────────────────────────────────────┐   │
│  │          UI LAYER (Console-Based Handlers)          │   │
│  │  ├─ AuthUIHandler          (Login/Registration)     │   │
│  │  ├─ MemberUIHandler        (Member Portal)          │   │
│  │  ├─ TrainerUIHandler       (Trainer Dashboard)      │   │
│  │  ├─ AdminUIHandler         (Admin Control Panel)    │   │
│  │  └─ UIHelper               (Output Formatting)      │   │
│  └──────────────────────────────────────────────────────┘   │
│                            ↓                                  │
│  ┌──────────────────────────────────────────────────────┐   │
│  │       BUSINESS LOGIC LAYER (Menu Handlers)          │   │
│  │  ├─ ClassManagementUIHandler                        │   │
│  │  ├─ UserManagementUIHandler                         │   │
│  │  ├─ MerchandiseUIHandler                            │   │
│  │  ├─ ReportsUIHandler                                │   │
│  │  └─ AdminMaintenanceUIHandler                       │   │
│  └──────────────────────────────────────────────────────┘   │
│                            ↓                                  │
│  ┌──────────────────────────────────────────────────────┐   │
│  │         DATA ACCESS LAYER (DAO Pattern)             │   │
│  │  ├─ AuthDAO        (User Authentication)            │   │
│  │  ├─ MemberDAO      (Member Operations)              │   │
│  │  ├─ TrainerDAO     (Trainer Operations)             │   │
│  │  ├─ AdminDAO       (Admin Operations)               │   │
│  │  ├─ GymClassDAO    (Class Management)               │   │
│  │  ├─ MerchandiseDAO (Merchandise Management)         │   │
│  │  └─ IBaseDAO       (Base Interface)                 │   │
│  └──────────────────────────────────────────────────────┘   │
│                            ↓                                  │
│  ┌──────────────────────────────────────────────────────┐   │
│  │            MODEL LAYER (Domain Objects)             │   │
│  │  ├─ User           (Base User Class)                │   │
│  │  ├─ UserAuth       (Authentication Session)         │   │
│  │  ├─ Member         (Member Entity)                  │   │
│  │  ├─ Trainer        (Trainer Entity)                 │   │
│  │  ├─ Admin          (Admin Entity)                   │   │
│  │  ├─ GymClass       (Class Entity)                   │   │
│  │  └─ Merchandise    (Product Entity)                 │   │
│  └──────────────────────────────────────────────────────┘   │
│                            ↓                                  │
│  ┌──────────────────────────────────────────────────────┐   │
│  │      DATABASE LAYER (PostgreSQL + JDBC)             │   │
│  │  └─ DatabaseConnection (Connection Management)      │   │
│  └──────────────────────────────────────────────────────┘   │
│                            ↓                                  │
│  ┌──────────────────────────────────────────────────────┐   │
│  │         PostgreSQL Database (gym_db)                │   │
│  └──────────────────────────────────────────────────────┘   │
│                                                               │
└─────────────────────────────────────────────────────────────┘
```

### Package Structure

```
com.keyin.gymmanagement/
├── App.java                          (Application Entry Point)
├── dao/                              (Data Access Objects)
│   ├── IBaseDAO.java
│   ├── AuthDAO.java
│   ├── MemberDAO.java
│   ├── TrainerDAO.java
│   ├── AdminDAO.java
│   ├── GymClassDAO.java
│   └── MerchandiseDAO.java
├── db/                               (Database Configuration)
│   └── DatabaseConnection.java
├── models/                           (Domain Models)
│   ├── User.java
│   ├── UserAuth.java
│   ├── Member.java
│   ├── Trainer.java
│   ├── Admin.java
│   ├── GymClass.java
│   └── Merchandise.java
├── security/                         (Security Utilities)
│   └── PasswordUtil.java
└── ui/                               (User Interface Handlers)
    ├── UIHelper.java
    ├── InputHelper.java
    ├── AuthUIHandler.java
    ├── MemberUIHandler.java
    ├── TrainerUIHandler.java
    ├── AdminUIHandler.java
    ├── ClassManagementUIHandler.java
    ├── UserManagementUIHandler.java
    ├── MerchandiseUIHandler.java
    ├── ReportsUIHandler.java
    └── AdminMaintenanceUIHandler.java
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 17+** - Download from [Eclipse Temurin](https://adoptium.net/)
- **Maven 3.8+** - Download from [Apache Maven](https://maven.apache.org/)
- **PostgreSQL 12+** - Download from [PostgreSQL](https://www.postgresql.org/)

### Database Setup

1. Create the database schema:

```bash
psql -U postgres -f gym_db_schema.sql
```

2. Verify the connection details in `DatabaseConnection.java`:
   - Host: `127.0.0.1`
   - Port: `5432`
   - Database: `gym_db`
   - User: `postgres`

### Project Build

1. Clone the repository:

```bash
git clone https://github.com/MattReid1982/SprintFinal_SEM3-Java-GymManagementSystem.git
cd SprintFinal_SEM3-Java-GymManagementSystem
```

2. Build the project:

```bash
mvn clean package
```

3. Run the application:

```bash
mvn exec:java@run
```

> Run these commands from the project root: `/Users/student2/Documents/Keyin/SprintFinal_SEM3-Java-GymManagementSystem`.

Members Responsibilities:

Person 1 - Matt

(Handles the data and business logic
Responsibilities:
Create classes (e.g., User, Member, Trainer, Admin, etc.)
Define attributes, constructors, getters/setters
Implement core logic methods (e.g., calculations, rules, validations)
Handle file/database structure if needed (basic data representation))

issues: Github integration for matching the branches.

Person 2 - Charles

(Handles what the system does
Responsibilities:
Implement system features:
Add / remove / update users
Booking classes
Payments / subscriptions
Create service/helper classes (e.g., GymService, BookingManager)
Connect logic from Person 1 to actual functionality)

Issues:

Person 3 - Keith

(Handles interaction and flow

Responsibilities:

Build the main() method
Create menus (console UI)
Handle user input/output
Call methods from Person 2
Control program flow)

<<<<<<< HEAD
Issues:
=======
- User management (create/edit/delete users)
- Class management and scheduling
- Merchandise inventory management
- Generate comprehensive reports
- Database and system maintenance

### Test Credentials

The database comes pre-populated with test accounts for demonstration:

| Username | Password   | Role    |
| -------- | ---------- | ------- |
| trainer1 | trainer123 | Trainer |
| admin    | admin123   | Admin   |
| member1  | member123  | Member  |

**Note:** Use these credentials to test the system's functionality across different user roles.

---

## 🗄️ Database Schema

### Key Tables

- **users_auth** - User credentials and authentication
- **members** - Member profiles and membership details
- **trainers** - Trainer information and specialization
- **admins** - Admin user accounts
- **gym_classes** - Class definitions and schedules
- **merchandise** - Product inventory and pricing

For full schema details, see `gym_db_schema.sql`

---

## 🔒 Security Features

- **Password Hashing** - Secure password storage using bcrypt-style hashing
- **Role-Based Access Control (RBAC)** - User roles determine available functions
- **Session Management** - UserAuth objects track authenticated sessions
- **Input Validation** - Comprehensive input validation across all handlers

---

## 🎨 UI/UX Features

- **Colored Console Output** - ANSI color codes for enhanced readability
- **Clear Terminal** - Fresh screen on each menu transition
- **Structured Menus** - Box-drawn borders and consistent formatting
- **Status Messages** - Success, error, and warning messages with icons
- **Interactive Prompts** - User-friendly input guidance and validation

---

## 📦 Dependencies

Key Maven dependencies configured in `pom.xml`:

- **Java 17 Compiler** - Source and target Java 17
- **PostgreSQL JDBC Driver** - Database connectivity
- **Apache Maven Plugins** - Build and execution

---

## ✅ Code Quality & Checkstyle

This project uses **Maven Checkstyle Plugin** for automatic code quality verification and Google Java Style Guide compliance.

### Quick Check

Run checkstyle validation:

```bash
mvn checkstyle:check
```

For detailed information on Checkstyle configuration, usage, and best practices, see [**checkstyle.md**](./checkstyle.md).

### Key Features

- **Google Style Compliance** - Validates code against Google Java Style Guide (80-character line limit, proper indentation, naming conventions)
- **Automatic Validation** - Runs during Maven build validation phase
- **Console Output** - Real-time feedback on style violations
- **Non-Blocking** - Violations reported but don't prevent builds (failsOnError: false)

---

## 🤝 Contributing

This is a collaborative student project. Team contributions:

1. **Keith Bishop** - Database architecture, DAO implementations, and complete UI design/implementation
2. **Matt Reid** - Project management and documentation
3. **Charles Rubia** - Business logic, validation, and reporting

---

## � Development Roles & Responsibilities

This section outlines the specialized development roles and responsibilities for team members working on the gym management system:

### 👤 Person 1 – Core Logic / Models

**Handles the data and business logic**

**Responsibilities:**

- Create domain classes (e.g., User, Member, Trainer, Admin, GymClass, Merchandise)
- Define attributes, constructors, getters/setters for all model entities
- Implement core logic methods (e.g., calculations, rules, validations)
- Handle file/database structure and basic data representation
- Ensure models follow OOP principles and best practices

### 👤 Person 2 – Functional Features / Services

**Handles what the system does**

**Responsibilities:**

- Implement system features:
  - Add / remove / update users
  - Class bookings and enrollment management
  - Payments and subscription handling
- Create service/helper classes (e.g., GymService, BookingManager, PaymentProcessor)
- Implement DAO classes for data persistence
- Connect logic from Person 1 to actual functionality
- Build business workflows and data operations

### 👤 Person 3 – User Interface / Main Program

**Handles interaction and flow**

**Responsibilities:**

- Build the main() method and application entry point
- Create console-based menus and navigation
- Handle user input/output and validation
- Call service methods from Person 2
- Control program flow and session management
- Implement UI handlers for all user roles
- Ensure consistent and intuitive user experience

---

## �📝 Code Documentation

All model classes include JavaDoc comments for:

- Class descriptions
- Constructor documentation
- Getter method descriptions
- Setter method descriptions
- Business logic method documentation

---

## � Recent Updates (Version 1.0 Final)

### Bug Fixes & Enhancements

- ✅ **Enhanced Login Error Handling** - Added clear "Bad username or password" message with pause prompt when login fails
- ✅ **Pause Prompts in Display Methods** - All data display methods now include "Press Enter to continue..." pause to prevent screen overflow
- ✅ **Fixed Class Management Display** - View all classes displays properly with pause (fixes trainer and admin class viewing)
- ✅ **Fixed Admin & Maintenance Menu** - Database status and system information display with proper pauses
- ✅ **Improved User Experience** - Consistent flow control and error messaging across all UI handlers
- ✅ **Database Connectivity** - PostgreSQL JDBC driver properly bundled and configured
- ✅ **Authentication System** - Password verification using bcrypt hashing with proper error handling
- ✅ **Removed Incomplete Features** - Cleaned up UI by removing non-functional "coming soon" placeholders:
  - Admin & Maintenance: Settings menu removed
  - Reports: Revenue Report removed
  - Class Management: Edit Class removed

### Verified Working Features

- Login/Registration flow with proper error messages
- All trainer menus (Class Management, View Members, Reports)
- All admin menus (User Management, Class Management, Merchandise, Maintenance)
- Member dashboard and purchasing
- All 4 core reports (Member Summary, Class Enrollment, Trainer Statistics, Merchandise Inventory)
- Class management (View, Add, Delete, Enroll, Remove)
- User management (View, Add, Edit, Delete)

---

## 🐛 Known Limitations

- Console-based UI (no graphical interface)
- Single-user session at a time
- Local database only (no remote connection support)

---

## 📚 Learning Objectives

This project demonstrates:

- **Object-Oriented Design** - Inheritance, encapsulation, and polymorphism
- **Design Patterns** - DAO pattern, MVC-inspired architecture
- **Database Integration** - JDBC, SQL operations, connection management
- **User Interface Design** - Console UI, input handling, output formatting
- **Software Architecture** - Layered architecture, separation of concerns
- **Team Development** - Git collaboration, code organization, documentation

---

## 📄 License

MIT License - See LICENSE file for details

---

## 📧 Contact

**Project Repository:** [GitHub](https://github.com/MattReid1982/SprintFinal_SEM3-Java-GymManagementSystem)

**Keyin College:** [Official Website](https://www.keyincollege.ca/)

---

**Last Updated:** April 20, 2026  
**Version:** 1.0 (Final - Production Ready)

---

_Built with ☕ Java and 💪 determination by Keith Bishop, Matt Reid, and Charles Rubia_
>>>>>>> 91c7a5fe7a9abe9da4b18623c228ea93699b5afe
