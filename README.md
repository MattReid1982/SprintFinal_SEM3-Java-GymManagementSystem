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

Issues:
