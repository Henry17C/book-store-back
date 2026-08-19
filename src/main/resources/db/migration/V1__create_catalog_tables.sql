-- V1__create_catalog_tables.sql

-- =========================================
-- 1. Tabla principal de Libros
-- =========================================
CREATE TABLE books (
    id UNIQUEIDENTIFIER PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    format VARCHAR(50) NOT NULL,
    language VARCHAR(50) NOT NULL,
    release_date DATE NOT NULL,
    synopsis VARCHAR(MAX) NOT NULL,
    
    -- Value Object Money aplanado en columnas
    price_amount DECIMAL(10, 2) NOT NULL,
    price_currency VARCHAR(3) NOT NULL,
    
    -- Otros campos
    cover_url VARCHAR(500),
    has_stock BIT NOT NULL DEFAULT 1,         -- 1 = TRUE
    is_recommended BIT NOT NULL DEFAULT 0,    -- 0 = FALSE
    status VARCHAR(50) NOT NULL, 
    average_rating DECIMAL(3, 2) DEFAULT 0.0,
    total_reviews INT DEFAULT 0
);

-- =========================================
-- 2. Tabla de Autores
-- =========================================
CREATE TABLE authors (
    id UNIQUEIDENTIFIER PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    biography VARCHAR(MAX)
);

-- =========================================
-- 3. Tabla intermedia (Relación N:M)
-- =========================================
CREATE TABLE book_authors (
    book_id UNIQUEIDENTIFIER NOT NULL,
    author_id UNIQUEIDENTIFIER NOT NULL,
    PRIMARY KEY (book_id, author_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
);

-- =========================================
-- 4. Tabla de Resenas (Reviews)
-- =========================================
CREATE TABLE reviews (
    id UNIQUEIDENTIFIER PRIMARY KEY,
    book_id UNIQUEIDENTIFIER NOT NULL,
    user_id UNIQUEIDENTIFIER NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment VARCHAR(MAX) NOT NULL,
    

    created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    updated_at DATETIME2,
    
    is_archived BIT NOT NULL DEFAULT 0,       -- 0 = FALSE
    CONSTRAINT fk_review_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);