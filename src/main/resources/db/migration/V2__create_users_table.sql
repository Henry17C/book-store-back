
-- =========================================
-- 1. Tabla principal de Usuarios
-- =========================================
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    full_name TEXT NOT NULL,
    password VARCHAR(255),
    role VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    provider VARCHAR(50) NOT NULL,
    picture_url VARCHAR(MAX)
);