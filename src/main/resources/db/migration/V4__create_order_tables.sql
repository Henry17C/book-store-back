-- =========================================
-- 1. Creación de la tabla principal: orders con sus embeddables
-- =========================================

CREATE TABLE orders (
    id UNIQUEIDENTIFIER NOT NULL,
    customer_id VARCHAR(255) NOT NULL,
    created_at DATETIME2 NOT NULL,
    status VARCHAR(50) NOT NULL,
    cancellation_reason VARCHAR(255),

    -- Campos embebidos de ShippingAddressEmbeddable
    shipping_province VARCHAR(255),
    shipping_city VARCHAR(255),
    shipping_sector VARCHAR(255),
    shipping_place VARCHAR(255),
    shipping_phone VARCHAR(50),
    shipping_recipient VARCHAR(255),
    shipping_main_street VARCHAR(255),
    shipping_number VARCHAR(50),
    shipping_cross_street VARCHAR(255),
    shipping_neighborhood VARCHAR(255),
    shipping_reference VARCHAR(255),
    shipping_zip_code VARCHAR(50),
    shipping_country VARCHAR(100),

    -- Campos embebidos de ShippingCostEmbeddable
    shipping_cost_base_amount DECIMAL(19, 4),
    shipping_cost_iva_amount DECIMAL(19, 4),
    shipping_cost_total_amount DECIMAL(19, 4),

    CONSTRAINT pk_orders PRIMARY KEY (id)
);

-- =========================================
-- 2. Creación de la tabla de detalles (order_items)
-- =========================================

CREATE TABLE order_items (
    id UNIQUEIDENTIFIER NOT NULL,
    book_id UNIQUEIDENTIFIER NOT NULL,
    price_amount DECIMAL(19, 4) NOT NULL,
    price_currency VARCHAR(10) NOT NULL,
    quantity INT NOT NULL,
    format VARCHAR(50) NOT NULL,
    order_id UNIQUEIDENTIFIER NOT NULL,

    CONSTRAINT pk_order_items PRIMARY KEY (id),
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(id)
);