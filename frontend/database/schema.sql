-- ============================================
-- TOURISM BOOKING SYSTEM DATABASE SCHEMA
-- ============================================

-- Create database
CREATE DATABASE IF NOT EXISTS tourism_booking;
USE tourism_booking;

-- ============================================
-- USERS TABLE
-- ============================================
CREATE TABLE users (
    user_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    role ENUM('TOURIST', 'ADMINISTRATOR') DEFAULT 'TOURIST',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- SERVICES TABLE
-- ============================================
CREATE TABLE services (
    service_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    type ENUM('DESTINATION', 'HOTEL', 'TOUR') NOT NULL,
    location VARCHAR(200),
    price DECIMAL(10, 2) NOT NULL,
    rating DECIMAL(2, 1) DEFAULT 0.0,
    review_count INT DEFAULT 0,
    description TEXT,
    image_url VARCHAR(500),
    features JSON,
    amenities JSON,
    availability BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_type (type),
    INDEX idx_location (location),
    INDEX idx_price (price),
    FULLTEXT INDEX idx_search (name, location, description)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- BOOKINGS TABLE
-- ============================================
CREATE TABLE bookings (
    booking_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    service_id VARCHAR(50) NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    travelers INT DEFAULT 1,
    total_amount DECIMAL(10, 2) NOT NULL,
    requirements TEXT,
    status ENUM('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED') DEFAULT 'PENDING',
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services(service_id) ON DELETE CASCADE,
    INDEX idx_user (user_id),
    INDEX idx_status (status),
    INDEX idx_booking_date (booking_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- PAYMENTS TABLE
-- ============================================
CREATE TABLE payments (
    payment_id VARCHAR(50) PRIMARY KEY,
    booking_id VARCHAR(50) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    method ENUM('mobile_money', 'card', 'paypal') NOT NULL,
    status ENUM('PENDING', 'PROCESSING', 'COMPLETED', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
    transaction_ref VARCHAR(100),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE,
    INDEX idx_booking (booking_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- REVIEWS TABLE
-- ============================================
CREATE TABLE reviews (
    review_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    service_id VARCHAR(50) NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services(service_id) ON DELETE CASCADE,
    INDEX idx_service (service_id),
    UNIQUE KEY unique_review (user_id, service_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- WISHLIST TABLE
-- ============================================
CREATE TABLE wishlist (
    wishlist_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    service_id VARCHAR(50) NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services(service_id) ON DELETE CASCADE,
    UNIQUE KEY unique_wishlist (user_id, service_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- INSERT SAMPLE DATA
-- ============================================

-- Admin user (password: admin123)
INSERT INTO users (user_id, name, email, password, role) VALUES
('ADMIN-001', 'System Administrator', 'admin@tourbookcameroon.cm', 'admin123', 'ADMINISTRATOR');

-- Sample services
INSERT INTO services (service_id, name, type, location, price, rating, review_count, description, image_url) VALUES
('SRV-001', 'Mount Cameroon', 'DESTINATION', 'Buea, Southwest Region', 25000, 4.8, 234, 'Experience Africa\'s highest peak with guided hiking tours through lush forests and volcanic landscapes.', 'https://images.unsplash.com/photo-1590523277543-a94d2e4eb00b?w=600'),
('SRV-002', 'Kribi Beach', 'DESTINATION', 'South Region', 35000, 4.9, 312, 'Relax on pristine white sandy beaches, swim in crystal clear waters, and enjoy fresh seafood by the Atlantic Ocean.', 'https://images.unsplash.com/photo-1580062089779-3a21e2d6ff37?w=600'),
('SRV-003', 'Waza National Park', 'DESTINATION', 'Far North Region', 50000, 4.6, 187, 'Discover diverse wildlife including lions, elephants, giraffes, and over 379 species of birds in their natural habitat.', 'https://images.unsplash.com/photo-1544735716-392fe2489ffa?w=600'),
('SRV-004', 'Hilton Yaoundé', 'HOTEL', 'Yaoundé Centre', 85000, 4.5, 120, 'Luxury hotel in the heart of Yaoundé with world-class amenities.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=600'),
('SRV-005', 'Sawa Hotel Douala', 'HOTEL', 'Bonanjo, Douala', 120000, 4.7, 98, 'Premium business hotel with spa, restaurant, and conference facilities.', 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=600'),
('SRV-006', 'Adventure Cameroon Tour', 'TOUR', 'Multiple Regions', 250000, 4.8, 56, '7-day complete adventure experience across Cameroon\'s best destinations.', 'https://images.unsplash.com/photo-1469854523086-cc02fe5d8800?w=600');
