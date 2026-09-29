package com.example.book_store_back.orders.domain;

public enum OrderStatus {
    CREATED,   // 1. Nace en la BD. Esperando confirmación de stock.
    RESERVED,  // 2. Inventario confirmó. Lista para pagarse.
    PAID,      // 3. Pago exitoso.
    SHIPPED,   // 4. Enviada al cliente.
    CANCELLED  // 5. Falló el stock, falló el pago o se canceló manualmente.
}