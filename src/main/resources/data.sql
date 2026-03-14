-- ============================================
-- BOOK STORE DATABASE (REFRACTOR VERSION)
-- ============================================

CREATE DATABASE book_store;
USE book_store;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

-- ============================================
-- AUTHORS
-- ============================================

CREATE TABLE authors (
    author_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_name VARCHAR(100) NOT NULL UNIQUE,
    date_of_birth DATE
);

-- ============================================
-- CATEGORIES
-- ============================================

CREATE TABLE categories (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE
);

-- ============================================
-- BOOKS
-- ============================================

CREATE TABLE books (
    book_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    image_url VARCHAR(255),
    category_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    description TEXT,
    publisher VARCHAR(150),
    publication_year INT,
    dimensions VARCHAR(50),

    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors(author_id) ON DELETE CASCADE
);

-- ============================================
-- USERS
-- ============================================

CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    image_url VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(20),
    dob DATE
);

-- ============================================
-- ROLES
-- ============================================

CREATE TABLE roles (
    role_name VARCHAR(50) PRIMARY KEY,
    description VARCHAR(255)
);

-- ============================================
-- PERMISSIONS
-- ============================================

CREATE TABLE permissions (
    permission_name VARCHAR(50) PRIMARY KEY
);

-- ============================================
-- ROLE PERMISSIONS
-- ============================================

-- CREATE TABLE role_permissions (
--     role_name VARCHAR(50),
--     permission_name VARCHAR(50),

--     PRIMARY KEY(role_name, permission_name),

--     FOREIGN KEY (role_name) REFERENCES roles(role_name) ON DELETE CASCADE,
--     FOREIGN KEY (permission_name) REFERENCES permissions(permission_name) ON DELETE CASCADE
-- );

-- ============================================
-- USER ROLES
-- ============================================

-- CREATE TABLE users_roles (
--     user_id BIGINT,
--     role_name VARCHAR(50),

--     PRIMARY KEY(user_id, role_name),

--     FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
--     FOREIGN KEY (role_name) REFERENCES roles(role_name) ON DELETE CASCADE
-- );

-- ============================================
-- CARTS
-- ============================================

CREATE TABLE carts (
    cart_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    created_at DATETIME,
    status VARCHAR(50),

    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- ============================================
-- CART ITEMS
-- ============================================

CREATE TABLE cart_items (
    cart_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT,
    book_id BIGINT,
    quantity INT,
    price DECIMAL(10,2),

    UNIQUE(cart_id, book_id),

    FOREIGN KEY (cart_id) REFERENCES carts(cart_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

-- ============================================
-- COMMENTS
-- ============================================

CREATE TABLE comments (
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT,
    user_id BIGINT,
    content TEXT NOT NULL,
    created_at DATETIME,

    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- ============================================
-- BOOK DISCOUNTS
-- ============================================

CREATE TABLE book_discounts (
    book_discount_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    discount_name VARCHAR(100),
    discount_code VARCHAR(50) UNIQUE,
    book_id BIGINT,
    start_date DATE,
    end_date DATE,

    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE
);

-- ============================================
-- ORDER DISCOUNTS
-- ============================================

CREATE TABLE order_discounts (
    order_discount_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    discount_code VARCHAR(100) UNIQUE,
    discount_name VARCHAR(100),
    discount_type VARCHAR(50),
    discount_value DECIMAL(10,2),
    min_order_amount DECIMAL(10,2),
    start_date DATE,
    end_date DATE
);

-- ============================================
-- ORDERS
-- ============================================

CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    order_discount_id BIGINT,
    order_date DATETIME,
    total_amount DECIMAL(10,2),
    status VARCHAR(50),

    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (order_discount_id) REFERENCES order_discounts(order_discount_id)
);

-- ============================================
-- ORDER ITEMS
-- ============================================

CREATE TABLE order_items (
    order_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    book_id BIGINT,
    quantity INT,
    price DECIMAL(10,2),
    subtotal DECIMAL(10,2),

    UNIQUE(order_id, book_id),

    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

-- ============================================
-- PAYMENTS
-- ============================================

CREATE TABLE payments (
    payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    user_id BIGINT,
    amount DECIMAL(10,2),
    payment_date DATETIME,
    qr_code TEXT,
    status VARCHAR(50),

    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- ============================================
-- POSTS (BLOG)
-- ============================================

CREATE TABLE posts (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(300) NOT NULL,
    content TEXT,
    description TEXT,
    slug VARCHAR(255) UNIQUE,
    thumbnail VARCHAR(255),
    status INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    published_at DATETIME,
    created_by BIGINT,
    modified_by BIGINT,

    FOREIGN KEY (created_by) REFERENCES users(user_id) ON DELETE SET NULL,
    FOREIGN KEY (modified_by) REFERENCES users(user_id) ON DELETE SET NULL
);

-- ============================================
-- PASSWORD RESET TOKENS
-- ============================================

CREATE TABLE password_reset_tokens (
    id VARCHAR(255) PRIMARY KEY,
    user_id BIGINT,
    token VARCHAR(255) UNIQUE,
    expiry_time DATETIME,

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- ============================================
-- REFRESH TOKENS
-- ============================================

CREATE TABLE refresh_tokens (
    id VARCHAR(255) PRIMARY KEY,
    user_id BIGINT,
    token VARCHAR(255) UNIQUE,
    expiry_time DATETIME,

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- ============================================
-- INSERT BASIC DATA
-- ============================================

INSERT IGNORE INTO roles VALUES
('ROLE_ADMIN','Administrator'),
('ROLE_USER','Normal User');

INSERT INTO permissions VALUES
('READ_BOOK'),
('WRITE_BOOK'),
('DELETE_BOOK'),
('READ_ORDER'),
('WRITE_ORDER'),
('DELETE_ORDER'),
('READ_USER'),
('WRITE_USER'),
('DELETE_USER');

INSERT INTO role_permissions VALUES
('ROLE_ADMIN','READ_BOOK'),
('ROLE_ADMIN','WRITE_BOOK'),
('ROLE_ADMIN','DELETE_BOOK'),
('ROLE_ADMIN','READ_ORDER'),
('ROLE_ADMIN','WRITE_ORDER'),
('ROLE_ADMIN','DELETE_ORDER'),
('ROLE_ADMIN','READ_USER'),
('ROLE_ADMIN','WRITE_USER'),
('ROLE_ADMIN','DELETE_USER'),

('ROLE_USER','READ_BOOK'),
('ROLE_USER','READ_ORDER'),
('ROLE_USER','WRITE_ORDER');
INSERT INTO authors (author_name, date_of_birth) VALUES
('J.K. Rowling', '1965-07-31'),
('George Orwell', '1903-06-25'),
('Haruki Murakami', '1949-01-12'),
('Paulo Coelho', '1947-08-24'),
('Dale Carnegie', '1888-11-24');

INSERT INTO categories (category_name) VALUES
('Fantasy'),
('Science Fiction'),
('Novel'),
('Self Help'),
('Classic Literature');

INSERT INTO books 
(title, image_url, category_id, author_id, price, description, publisher, publication_year, dimensions)
VALUES
(
'Câu hỏi về hóa học',
'https://res.cloudinary.com/domnsybxh/image/upload/v1749030415/txbscqujkfap7bpn3qs3.jpg',
1,
1,
19.99,
'The first book in the Harry Potter series.',
'Bloomsbury',
1997,
'13 x 20 cm'
),

(
'Dế Mèn Phiêu Lưu Ký',
'https://res.cloudinary.com/domnsybxh/image/upload/v1749027654/kzeeubkxzdmvnfd9vadc.webp',
5,
2,
15.50,
'Dystopian novel about totalitarian society.',
'Secker & Warburg',
1949,
'13 x 20 cm'
),

(
'Spring boot',
'https://res.cloudinary.com/domnsybxh/image/upload/v1748159338/ui5tvctdyoqnfijct0bq.png',
3,
3,
18.75,
'A surreal novel blending reality and fantasy.',
'Shinchosha',
2002,
'14 x 21 cm'
),

(
'Thỏ bảy màu',
'https://res.cloudinary.com/domnsybxh/image/upload/v1748159252/gcuwhxitijugkbjnm64f.jpg',
3,
4,
14.90,
'A philosophical novel about following your dreams.',
'HarperCollins',
1988,
'13 x 20 cm'
),

(
'Chú chim tý hon',
'https://res.cloudinary.com/domnsybxh/image/upload/v1748159005/cjqwv2u4gcfsx9dnq15o.webp',
4,
5,
16.99,
'Classic self-help book on communication and relationships.',
'Simon & Schuster',
1936,
'14 x 21 cm'
),
(
'Nhớ một thời đã đã quên',
'https://res.cloudinary.com/domnsybxh/image/upload/v1748158939/ikecxoaqoxk1wgwpnznr.webp',
1,
1,
19.99,
'The first book in the Harry Potter series.',
'Bloomsbury',
1997,
'13 x 20 cm'
),

(
'Câu chuyện doanh nhân',
'https://res.cloudinary.com/domnsybxh/image/upload/v1748158816/qw7zwzrmhrg3fyoruzlb.webp',
5,
2,
15.50,
'Dystopian novel about totalitarian society.',
'Secker & Warburg',
1949,
'13 x 20 cm'
),

(
'Victor hugo',
'https://res.cloudinary.com/domnsybxh/image/upload/v1748158261/ivtqnb6fkax71eldirsu.jpg',
3,
3,
18.75,
'A surreal novel blending reality and fantasy.',
'Shinchosha',
2002,
'14 x 21 cm'
),

(
'Tắt đèn',
'https://res.cloudinary.com/domnsybxh/image/upload/v1747752479/kitbgqwjxzm8hdfi2vhn.jpg',
3,
4,
14.90,
'A philosophical novel about following your dreams.',
'HarperCollins',
1988,
'13 x 20 cm'
),

(
'Chí phèo',
'https://res.cloudinary.com/domnsybxh/image/upload/v1747752457/epnnfvblhbch3osnj48e.jpg',
4,
5,
16.99,
'Classic self-help book on communication and relationships.',
'Simon & Schuster',
1936,
'14 x 21 cm'
),
(
'Lập trình java',
'https://res.cloudinary.com/domnsybxh/image/upload/v1747752311/fvv78c7vnesnjvasov7s.png',
1,
1,
19.99,
'The first book in the Harry Potter series.',
'Bloomsbury',
1997,
'13 x 20 cm'
),

(
'Ứng dụng trí tuệ nhân tạo để dẫn đầu',
'https://res.cloudinary.com/domnsybxh/image/upload/v1747752105/ex6s9nt14liby07eukwh.jpg',
5,
2,
15.50,
'Dystopian novel about totalitarian society.',
'Secker & Warburg',
1949,
'13 x 20 cm'
),

(
'Trí tuệ nhân tạo học máy và ứng dụng',
'https://res.cloudinary.com/domnsybxh/image/upload/v1747752077/dxzdazgkptbps755uo5a.webp',
3,
3,
18.75,
'A surreal novel blending reality and fantasy.',
'Shinchosha',
2002,
'14 x 21 cm'
),

(
'Triết học đông phương',
'https://res.cloudinary.com/domnsybxh/image/upload/v1747752049/ja20xgmjee5rvvrjhix8.jpg',
3,
4,
14.90,
'A philosophical novel about following your dreams.',
'HarperCollins',
1988,
'13 x 20 cm'
),

(
'AI trí tuệ nhân tạo',
'hhttps://res.cloudinary.com/domnsybxh/image/upload/v1747751998/h37l96wyejnaywbymtdy.jpg',
4,
5,
16.99,
'Classic self-help book on communication and relationships.',
'Simon & Schuster',
1936,
'14 x 21 cm'
),
(
'Số đỏ',
'https://res.cloudinary.com/domnsybxh/image/upload/v1747751860/xcawawyovktxrapmwwf0.jpg',
1,
1,
19.99,
'The first book in the Harry Potter series.',
'Bloomsbury',
1997,
'13 x 20 cm'
),
(
'Cây cam ngọt của tôi',
'https://res.cloudinary.com/domnsybxh/image/upload/v1747751784/qvfxhvnenjutebmkmpnu.jpg',
5,
2,
15.50,
'Dystopian novel about totalitarian society.',
'Secker & Warburg',
1949,
'13 x 20 cm'
);