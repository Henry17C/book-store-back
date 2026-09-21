package com.example.book_store_back.inventory.domain;

import java.time.LocalDateTime;
import java.util.Objects;

//Entity
public class StockMovement {
    private final StockMovementId id;
    private Quantity quantity;
    private final MovementType type;
    private final LocalDateTime createdAt;

    public StockMovement(StockMovementId id, Quantity quantity, MovementType type, LocalDateTime createdAt) {
        this.id = Objects.requireNonNull(id, "El id del movimiento no puede ser nulo.");
        this.quantity = Objects.requireNonNull(quantity, "La cantidad no puede ser nula.");
        this.type = Objects.requireNonNull(type, "El tipo de movimiento no puede ser nulo.");
        this.createdAt = Objects.requireNonNull(createdAt, "La fecha no puede ser nula.");
    }

    public StockMovementId getId() {
        return id;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public MovementType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
