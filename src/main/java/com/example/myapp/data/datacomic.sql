-- ===============================
-- DATABASE (one-file DDL)
-- ===============================
DROP DATABASE IF EXISTS comic_store;
CREATE DATABASE comic_store CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE comic_store;
-- ===============================
-- 1. ROLES
-- ===============================
CREATE TABLE roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
) ENGINE = InnoDB;
INSERT INTO roles (role_name)
VALUES ('ROLE_ADMIN'),
    ('ROLE_CUSTOMER');
-- ===============================
-- 2. USERS
-- ===============================
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    address VARCHAR(255),
    phone VARCHAR(20) UNIQUE,
    role_id INT NOT NULL,
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(role_id)
) ENGINE = InnoDB;
-- ===============================
-- 3. AUTHORS
-- ===============================
CREATE TABLE authors (
    author_id INT AUTO_INCREMENT PRIMARY KEY,
    author_name VARCHAR(100) NOT NULL UNIQUE,
    date_of_birth DATE
) ENGINE = InnoDB;
-- ===============================
-- 4. CATEGORIES
-- ===============================
CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    sort_order INT DEFAULT 0,
    status INT DEFAULT 1
) ENGINE = InnoDB;
-- ===============================
-- 5. GENRES
-- ===============================
CREATE TABLE genres (
    genre_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    status INT DEFAULT 1
) ENGINE = InnoDB;
-- ===============================
-- 6. COMICS
-- ===============================
CREATE TABLE comics (
    comic_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    cover_image VARCHAR(255),
    thumbnail VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    publisher VARCHAR(100),
    release_year INT,
    dimensions VARCHAR(50),
    status INT NOT NULL DEFAULT 1,
    author_id INT NOT NULL,
    genre_id INT NOT NULL,
    CONSTRAINT fk_comics_author FOREIGN KEY (author_id) REFERENCES authors(author_id),
    CONSTRAINT fk_comics_genre FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
) ENGINE = InnoDB;
-- ===============================
-- 7. CARTS
-- ===============================
CREATE TABLE carts (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDING', 'ORDERED', 'PAID') NOT NULL DEFAULT 'PENDING',
    CONSTRAINT fk_carts_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE = InnoDB;
-- ===============================
-- 8. CART ITEMS
-- ===============================
CREATE TABLE cart_items (
    cart_item_id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT NOT NULL,
    comic_id INT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_cart_items_cart FOREIGN KEY (cart_id) REFERENCES carts(cart_id) ON DELETE CASCADE,
    CONSTRAINT fk_cart_items_comic FOREIGN KEY (comic_id) REFERENCES comics(comic_id) ON DELETE CASCADE,
    CONSTRAINT uq_cart_comic UNIQUE (cart_id, comic_id),
    CONSTRAINT chk_quantity_positive CHECK (quantity > 0)
) ENGINE = InnoDB;
-- ===============================
-- 9. PROMOTIONS
-- ===============================
CREATE TABLE promotions (
    promotion_id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    discount_percent INT,
    start_date DATE,
    end_date DATE,
    status INT DEFAULT 1,
    CONSTRAINT chk_discount_percent CHECK (
        discount_percent BETWEEN 0 AND 100
    )
) ENGINE = InnoDB;
-- ===============================
-- 10. ORDERS
-- ===============================
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    status ENUM(
        'PENDING',
        'PAID',
        'SHIPPED',
        'DELIVERED',
        'CANCELLED'
    ) NOT NULL DEFAULT 'PENDING',
    promotion_id INT,
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_orders_promotion FOREIGN KEY (promotion_id) REFERENCES promotions(promotion_id) ON DELETE
    SET NULL
) ENGINE = InnoDB;
-- ===============================
-- 11. ORDER ITEMS
-- ===============================
CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    comic_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_comic FOREIGN KEY (comic_id) REFERENCES comics(comic_id) ON DELETE CASCADE,
    CONSTRAINT chk_order_quantity_positive CHECK (quantity > 0)
) ENGINE = InnoDB;
-- ===============================
-- 12. PAYMENTS (QR)
-- ===============================
CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    qr_code VARCHAR(255) NOT NULL,
    status ENUM('PENDING', 'COMPLETED', 'FAILED') NOT NULL DEFAULT 'PENDING',
    payment_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payments_order FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
) ENGINE = InnoDB;
-- ===============================
-- 13. COMMENTS
-- ===============================
CREATE TABLE comments (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    user_id INT NOT NULL,
    comic_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comments_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_comments_comic FOREIGN KEY (comic_id) REFERENCES comics(comic_id) ON DELETE CASCADE
) ENGINE = InnoDB;
-- ===============================
-- 14. IMAGES
-- ===============================
CREATE TABLE images (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    size INT,
    type VARCHAR(255),
    link VARCHAR(255) NOT NULL,
    public_id VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INT,
    CONSTRAINT fk_images_user FOREIGN KEY (created_by) REFERENCES users(user_id) ON DELETE
    SET NULL
) ENGINE = InnoDB;
-- ===============================
-- 15. POSTS
-- ===============================
CREATE TABLE posts (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(300) NOT NULL,
    content TEXT,
    description TEXT,
    slug VARCHAR(255) NOT NULL UNIQUE,
    thumbnail VARCHAR(255),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    published_at DATETIME NULL,
    status INT NOT NULL,
    created_by INT,
    modified_by INT,
    CONSTRAINT fk_posts_created_by FOREIGN KEY (created_by) REFERENCES users(user_id) ON DELETE
    SET NULL,
        CONSTRAINT fk_posts_modified_by FOREIGN KEY (modified_by) REFERENCES users(user_id) ON DELETE
    SET NULL
) ENGINE = InnoDB;
-- ===============================
-- Useful indexes
-- ===============================
CREATE INDEX idx_orders_user_date ON orders (user_id, order_date);
CREATE INDEX idx_comics_title ON comics (title);
CREATE INDEX idx_promotions_code ON promotions (code);
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_genres_name ON genres (name);