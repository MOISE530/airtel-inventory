-- Airtel Inventory Management System Database Setup
-- MySQL 8.0 Database Script

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS airtel_inventory;
USE airtel_inventory;

-- Drop existing tables if they exist (for clean setup)
DROP TABLE IF EXISTS audit_logs;
DROP TABLE IF EXISTS assignments;
DROP TABLE IF EXISTS devices;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS users;

-- Create Users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100),
    role VARCHAR(20) DEFAULT 'staff',
    INDEX idx_username (username)
);

-- Create Devices table
CREATE TABLE devices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asset_tag VARCHAR(50) UNIQUE,
    serial_number VARCHAR(100) UNIQUE,
    device_type VARCHAR(20),
    brand VARCHAR(50),
    model VARCHAR(100),
    specifications TEXT,
    device_condition VARCHAR(20) DEFAULT 'Good',
    status VARCHAR(20) DEFAULT 'Available',
    purchase_date DATE,
    warranty_until DATE,
    INDEX idx_asset_tag (asset_tag),
    INDEX idx_serial_number (serial_number),
    INDEX idx_status (status),
    INDEX idx_device_type (device_type)
);

-- Create Employees table
CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(20) UNIQUE NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    department VARCHAR(50),
    designation VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(20),
    INDEX idx_employee_id (employee_id),
    INDEX idx_full_name (full_name)
);

-- Create Assignments table
CREATE TABLE assignments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    device_id BIGINT,
    employee_id BIGINT,
    assigned_date DATE,
    return_date DATE,
    notes TEXT,
    INDEX idx_device_id (device_id),
    INDEX idx_employee_id (employee_id),
    INDEX idx_assigned_date (assigned_date)
);

-- Create Audit Logs table
CREATE TABLE audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    action VARCHAR(50),
    entity_type VARCHAR(50),
    entity_id VARCHAR(50),
    details TEXT,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(45),
    user_agent TEXT,
    INDEX idx_username (username),
    INDEX idx_action (action),
    INDEX idx_entity_type (entity_type),
    INDEX idx_timestamp (timestamp)
);

-- Insert sample data

-- Insert admin user
INSERT INTO users (username, password, full_name, role) VALUES 
('24RP01839', '24RP03971', 'Airtel Inventory Administrator', 'admin');

-- Insert sample employee
INSERT INTO employees (employee_id, full_name, department, designation, email, phone) VALUES 
('EMP001', 'John Doe', 'IT', 'Manager', 'john.doe@airtel.com', '+1234567890');

-- Insert sample devices for testing
INSERT INTO devices (asset_tag, serial_number, device_type, brand, model, specifications, device_condition, status, purchase_date, warranty_until) VALUES 
('LAP-001', 'SN123456789', 'Laptop', 'Dell', 'XPS 15', 'Intel i7, 16GB RAM, 512GB SSD', 'Good', 'Available', '2023-01-15', '2025-01-15'),
('LAP-002', 'SN987654321', 'Laptop', 'HP', 'EliteBook 840', 'Intel i5, 8GB RAM, 256GB SSD', 'Good', 'Available', '2023-02-20', '2025-02-20'),
('DT-001', 'SN456789123', 'Desktop', 'Lenovo', 'ThinkCentre', 'Intel i7, 16GB RAM, 1TB HDD', 'Good', 'Available', '2023-03-10', '2025-03-10'),
('MBL-001', 'SN789123456', 'Mobile', 'Apple', 'iPhone 14', '128GB Storage, 6.1 inch display', 'New', 'Available', '2023-04-05', '2025-04-05'),
('LAP-003', 'SN321654987', 'Laptop', 'Apple', 'MacBook Pro', 'M2 Pro, 16GB RAM, 512GB SSD', 'Good', 'Assigned', '2023-05-12', '2025-05-12');

-- Insert sample assignments
INSERT INTO assignments (device_id, employee_id, assigned_date, return_date, notes) VALUES 
(5, 1, '2023-06-01', NULL, 'Assigned to IT Manager for development work');

-- Display setup completion message
SELECT 'Database setup completed successfully!' as message;
SELECT COUNT(*) as total_users FROM users;
SELECT COUNT(*) as total_employees FROM employees;
SELECT COUNT(*) as total_devices FROM devices;
SELECT COUNT(*) as total_assignments FROM assignments;
