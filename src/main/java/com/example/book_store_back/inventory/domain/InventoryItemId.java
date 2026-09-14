package com.example.book_store_back.inventory.domain;

import java.util.Objects;
import java.util.UUID;
// VO
public record InventoryItemId(String value) {
    public InventoryItemId {
        Objects.requireNonNull(value, "El id del item no puede ser nulo.");
    }
    public static InventoryItemId generate() {
        return new InventoryItemId(UUID.randomUUID().toString());
    }
}