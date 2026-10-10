CREATE TABLE payment_jpa_entity (
    id UUID NOT NULL,
    order_id UUID NOT NULL,
    price_amount DECIMAL(19, 4) NOT NULL,
    price_currency VARCHAR(10) NOT NULL,
    status VARCHAR(50) NOT NULL,
    gateway_transaction_id VARCHAR(255) NOT NULL,
    
    CONSTRAINT pk_payments PRIMARY KEY (id),
    CONSTRAINT uk_gateway_transaction_id UNIQUE (gateway_transaction_id)
);