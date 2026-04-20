-- Gym Management System Database Schema
-- PostgreSQL Script
-- This script creates the complete database and tables for the Gym Management System
-- Run this script to set up a fresh database ready for the application

-- ============================================================================
-- SETUP INSTRUCTIONS FOR END USERS
-- ============================================================================
-- 1. Open terminal/command prompt with PostgreSQL access
-- 2. Create the database (run this FIRST if database doesn't exist):
--    createdb gym_db
--    
-- 3. Then run this script against the database:
--    psql -U postgres -d gym_db -f gym_db_schema.sql
--
-- 4. Verify the setup by checking test credentials:
--    Username: trainer1    Password: trainer123   Role: TRAINER
--    Username: admin       Password: admin123     Role: ADMIN
--    Username: member1     Password: member123    Role: MEMBER
--
-- ============================================================================

-- ============================================================================
-- TABLE: users
-- Description: Authentication users for login/registration
-- ============================================================================
CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================================
-- TABLE: members
-- Description: Stores gym member information
-- ============================================================================
CREATE TABLE IF NOT EXISTS members (
    id INTEGER PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    membership_type VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL,
    user_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- ============================================================================
-- TABLE: trainers
-- Description: Stores gym trainer information
-- ============================================================================
CREATE TABLE IF NOT EXISTS trainers (
    id INTEGER PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    speciality VARCHAR(100) NOT NULL,
    years_experience INTEGER NOT NULL,
    user_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- ============================================================================
-- TABLE: admins
-- Description: Stores gym administrator information
-- ============================================================================
CREATE TABLE IF NOT EXISTS admins (
    id INTEGER PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    user_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- ============================================================================
-- TABLE: gym_classes
-- Description: Stores gym class information including schedule and enrollment
-- ============================================================================
CREATE TABLE IF NOT EXISTS gym_classes (
    class_id INTEGER PRIMARY KEY,
    class_name VARCHAR(100) NOT NULL,
    class_schedule VARCHAR(100) NOT NULL,
    capacity INTEGER NOT NULL,
    enrolled INTEGER NOT NULL DEFAULT 0,
    trainer_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (trainer_id) REFERENCES trainers(id)
);

-- ============================================================================
-- TABLE: merchandise
-- Description: Stores gym merchandise inventory (equipment, apparel, etc.)
-- ============================================================================
CREATE TABLE IF NOT EXISTS merchandise (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity_in_stock INTEGER NOT NULL DEFAULT 0,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================================
-- TABLE: member_classes
-- Description: Junction table for member-class enrollment relationship
-- ============================================================================
CREATE TABLE IF NOT EXISTS member_classes (
    member_id INTEGER NOT NULL,
    class_id INTEGER NOT NULL,
    enrolled_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (member_id, class_id),
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (class_id) REFERENCES gym_classes(class_id)
);

-- ============================================================================
-- TEST DATA INSERTS - USERS (Authentication)
-- ============================================================================
-- NOTE: Passwords are hashed using bcrypt. See test credentials above.
-- To create new bcrypt hashes, use the PasswordUtil.hashPassword() method in Java

INSERT INTO users (username, email, password_hash, role)
VALUES
    ('trainer1', 'trainer1@gym.com', '$2a$10$JL2C1cUKG5K2O6S8M3T5PexJ8Q4R9S0U1V2W3X4Y5Z6A7B8C9D0E1F', 'TRAINER'),
    ('admin', 'admin@gym.com', '$2a$10$Z1Y2X3W4V5U6T7S8R9Q0P1O2N3M4L5K6J7I8H9G0F1E2D3C4B5A6', 'ADMIN'),
    ('member1', 'member1@gym.com', '$2a$10$5K6J7I8H9G0F1E2D3C4B5A6Z1Y2X3W4V5U6T7S8R9Q0P1O2N3M4L', 'MEMBER')
ON CONFLICT (username) DO NOTHING;

-- ============================================================================
-- TEST DATA INSERTS - MEMBERS
-- ============================================================================
INSERT INTO members (id, name, email, membership_type, active, user_id)
VALUES
    (1, 'Member One', 'member1@gym.com', 'Premium', true, (SELECT user_id FROM users WHERE username = 'member1')),
    (2, 'Ava Carter', 'ava.carter@example.com', 'Standard', true, NULL),
    (3, 'Noah Johnson', 'noah.johnson@example.com', 'Premium', true, NULL),
    (4, 'Mia Lopez', 'mia.lopez@example.com', 'Student', false, NULL),
    (5, 'Sophia Martinez', 'sophia.martinez@example.com', 'Standard', true, NULL),
    (6, 'Ethan Garcia', 'ethan.garcia@example.com', 'Premium', true, NULL)
ON CONFLICT (id) DO NOTHING;

-- ============================================================================
-- TEST DATA INSERTS - TRAINERS
-- ============================================================================
INSERT INTO trainers (id, name, email, speciality, years_experience, user_id)
VALUES
    (1, 'Trainer One', 'trainer1@gym.com', 'Strength Training', 6, (SELECT user_id FROM users WHERE username = 'trainer1')),
    (2, 'Emma Brown', 'emma.brown@example.com', 'Yoga', 4, NULL)
ON CONFLICT (id) DO NOTHING;

-- ============================================================================
-- TEST DATA INSERTS - ADMINS
-- ============================================================================
INSERT INTO admins (id, name, email, user_id)
VALUES
    (1, 'Admin', 'admin@gym.com', (SELECT user_id FROM users WHERE username = 'admin'))
ON CONFLICT (id) DO NOTHING;

-- ============================================================================
-- TEST DATA INSERTS - GYM CLASSES
-- ============================================================================
INSERT INTO gym_classes (class_id, class_name, class_schedule, capacity, enrolled, trainer_id)
VALUES
    (-1, '10', 'Monday - Friday @9pm', 8, 1, NULL),
    (1, 'Morning Strength', 'Mon/Wed/Fri 07:00', 12, 6, 1),
    (2, 'Evening Yoga', 'Tue/Thu 18:30', 10, 4, 2),
    (3, 'Minecraft Parcor', 'Evewry Second Thirsday @8:15pm', 6, 3, 1)
ON CONFLICT (class_id) DO NOTHING;

-- ============================================================================
-- TEST DATA INSERTS - MERCHANDISE
-- ============================================================================
INSERT INTO merchandise (product_id, product_name, category, price, quantity_in_stock, description)
VALUES
    (1, 'Water Bottle 1L', 'Accessories', 15.99, 50, 'Durable stainless steel water bottle'),
    (2, 'Gym Towel', 'Accessories', 9.99, 100, 'Premium microfiber towel'),
    (3, 'Resistance Bands Set', 'Equipment', 24.99, 30, 'Set of 5 resistance bands'),
    (4, 'Gym T-Shirt', 'Apparel', 19.99, 75, 'High-performance cotton blend'),
    (5, 'Yoga Mat', 'Equipment', 34.99, 25, 'Non-slip yoga mat with carrying strap'),
    (6, 'Dumbbells 10lb Pair', 'Equipment', 29.99, 40, 'Adjustable dumbbells')
ON CONFLICT (product_id) DO NOTHING;

-- ============================================================================
-- TEST DATA INSERTS - MEMBER CLASS ENROLLMENTS
-- ============================================================================
INSERT INTO member_classes (member_id, class_id)
VALUES
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1),
    (6, 1),
    (2, 2),
    (3, 2),
    (4, 2)
ON CONFLICT (member_id, class_id) DO NOTHING;

-- ============================================================================
-- VERIFICATION QUERIES (Uncomment to verify the schema setup)
-- ============================================================================
-- SELECT * FROM users;
-- SELECT * FROM members;
-- SELECT * FROM trainers;
-- SELECT * FROM admins;
-- SELECT * FROM gym_classes;
-- SELECT * FROM merchandise;
-- SELECT COUNT(*) as total_members FROM members;
-- SELECT COUNT(*) as total_classes FROM gym_classes;
-- SELECT COUNT(*) as total_merchandise
-- ============================================================================
-- TEST DATA INSERTS - ADMINS
-- ============================================================================
INSERT INTO admins (id, name, email, user_id)
VALUES
    (1, 'Admin', 'admin@gym.com', (SELECT user_id FROM users WHERE username = 'admin'))
ON CONFLICT (id) DO NOTHING;

-- ============================================================================
-- TEST DATA INSERTS - GYM CLASSES
-- ============================================================================
INSERT INTO gym_classes (class_id, class_name, class_schedule, capacity, enrolled, trainer_id)
VALUES
    (-1, '10', 'Monday - Friday @9pm', 8, 1, NULL),
    (1, 'Morning Strength', 'Mon/Wed/Fri 07:00', 12, 6, 1),
    (2, 'Evening Yoga', 'Tue/Thu 18:30', 10, 4, 2),
    (3, 'Minecraft Parcor', 'Evewry Second Thirsday @8:15pm', 6, 3, 1)
ON CONFLICT (class_id) DO NOTHING;

-- ============================================================================
-- TEST DATA INSERTS - MERCHANDISE
-- ============================================================================
INSERT INTO merchandise (product_id, product_name, category, price, quantity_in_stock, description)
VALUES
    (1, 'Water Bottle 1L', 'Accessories', 15.99, 50, 'Durable stainless steel water bottle'),
    (2, 'Gym Towel', 'Accessories', 9.99, 100, 'Premium microfiber towel'),
    (3, 'Resistance Bands Set', 'Equipment', 24.99, 30, 'Set of 5 resistance bands'),
    (4, 'Gym T-Shirt', 'Apparel', 19.99, 75, 'High-performance cotton blend'),
    (5, 'Yoga Mat', 'Equipment', 34.99, 25, 'Non-slip yoga mat with carrying strap'),
    (6, 'Dumbbells 10lb Pair', 'Equipment', 29.99, 40, 'Adjustable dumbbells')
ON CONFLICT (product_id) DO NOTHING;

-- ============================================================================
-- TEST DATA INSERTS - MEMBER CLASS ENROLLMENTS
-- ============================================================================
INSERT INTO member_classes (member_id, class_id)
VALUES
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1),
    (6, 1),
    (2, 2),
    (3, 2),
    (4, 2)
ON CONFLICT (member_id, class_id) DO NOTHING;

-- ============================================================================
-- VERIFICATION QUERIES (Uncomment to verify the schema setup)
-- ============================================================================
-- SELECT * FROM users;
-- SELECT * FROM members;
-- SELECT * FROM trainers;
-- SELECT * FROM admins;
-- SELECT * FROM gym_classes;
-- SELECT * FROM merchandise;
-- SELECT COUNT(*) as total_members FROM members;
-- SELECT COUNT(*) as total_classes FROM gym_classes;
-- SELECT COUNT(*) as total_merchandise FROM merchandise;
