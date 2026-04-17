-- Gym Management System Database Schema
-- PostgreSQL Script
-- This script creates the complete database and tables for the Gym Management System
-- Run this script against any PostgreSQL database to set up the system

-- Create database (if it doesn't exist)
-- Note: Execute this separately if running as a non-superuser
-- CREATE DATABASE gym_db;

-- Connect to gym_db before running the rest of this script
-- \c gym_db

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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================================
-- TABLE: admins
-- Description: Stores gym administrator information
-- ============================================================================
CREATE TABLE IF NOT EXISTS admins (
    id INTEGER PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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
-- TEST DATA INSERTS
-- ============================================================================

-- Insert test members
INSERT INTO members (id, name, email, membership_type, active)
VALUES
    (1, 'Ava Carter', 'ava.carter@example.com', 'Standard', true),
    (2, 'Noah Johnson', 'noah.johnson@example.com', 'Premium', true),
    (3, 'Mia Lopez', 'mia.lopez@example.com', 'Student', false)
ON CONFLICT (id) DO NOTHING;

-- Insert test trainers
INSERT INTO trainers (id, name, email, speciality, years_experience)
VALUES
    (1, 'Liam Smith', 'liam.smith@example.com', 'Strength Training', 6),
    (2, 'Emma Brown', 'emma.brown@example.com', 'Yoga', 4)
ON CONFLICT (id) DO NOTHING;

-- Insert test admins
INSERT INTO admins (id, name, email)
VALUES
    (1, 'Olivia Hall', 'olivia.hall@example.com')
ON CONFLICT (id) DO NOTHING;

-- Insert test gym classes
INSERT INTO gym_classes (class_id, class_name, class_schedule, capacity, enrolled, trainer_id)
VALUES
    (1, 'Morning Strength', 'Mon/Wed/Fri 07:00', 12, 0, 1),
    (2, 'Evening Yoga', 'Tue/Thu 18:30', 10, 0, 2)
ON CONFLICT (class_id) DO NOTHING;

-- Insert test merchandise
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
-- VERIFICATION QUERIES (Run these to verify the schema)
-- ============================================================================
-- SELECT * FROM members;
-- SELECT * FROM trainers;
-- SELECT * FROM admins;
-- SELECT * FROM gym_classes;
-- SELECT * FROM merchandise;
