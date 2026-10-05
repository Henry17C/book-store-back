package com.example.book_store_back.payment.domain;

public record GatewayTransactionId(String value) {
    public GatewayTransactionId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El ID de la transacción de la pasarela es obligatorio");
        }
    }
}