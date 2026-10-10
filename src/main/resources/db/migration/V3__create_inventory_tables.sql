-- =========================================
-- 1. Crear tabla principal: inventory_items
-- =========================================

CREATE TABLE inventory_items (
    id VARCHAR(36) NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    created_at DATETIME2 NOT NULL,
    stock INT NOT NULL,
    version BIGINT NOT NULL,
    CONSTRAINT PK_inventory_items PRIMARY KEY (id),
    CONSTRAINT UQ_inventory_items_product_id UNIQUE (product_id)
);

-- =========================================
-- 2. Crear tabla: stock_movements
-- =========================================

CREATE TABLE stock_movements (
    id VARCHAR(36) NOT NULL,
    quantity INT NOT NULL,
    movement_type VARCHAR(20) NOT NULL,
    created_at DATETIME2 NOT NULL,
    inventory_item_id VARCHAR(36) NOT NULL,
    CONSTRAINT PK_stock_movements PRIMARY KEY (id),
    CONSTRAINT FK_stock_movements_inventory_items FOREIGN KEY (inventory_item_id) 
        REFERENCES inventory_items (id) ON DELETE CASCADE
);

-- =========================================
-- 3. Crear tabla: inventory_reservations
-- =========================================
CREATE TABLE inventory_reservations (
    id BIGINT IDENTITY(1,1) NOT NULL,
    order_id VARCHAR(36) NOT NULL,
    quantity INT NOT NULL,
    expires_at DATETIME2 NOT NULL,
    inventory_item_id VARCHAR(36) NOT NULL,
    CONSTRAINT PK_inventory_reservations PRIMARY KEY (id),
    CONSTRAINT FK_inventory_reservations_inventory_items FOREIGN KEY (inventory_item_id) 
        REFERENCES inventory_items (id) ON DELETE CASCADE
);

-- =========================================
-- 4. Crear índices para optimizar las búsquedas por llave foránea
-- =========================================

CREATE INDEX IX_stock_movements_inventory_item_id ON stock_movements (inventory_item_id);
CREATE INDEX IX_inventory_reservations_inventory_item_id ON inventory_reservations (inventory_item_id);
