package com.example.book_store_back.inventory.domain;

import java.util.Objects;
import java.util.UUID;
// VO
public record StockMovementId(String value) {
    public StockMovementId {
        Objects.requireNonNull(value, "El valor del id del movimiento de stock no puede ser nulo.");
    }
    public static StockMovementId generate() {
        return new StockMovementId(UUID.randomUUID().toString());
    }
}