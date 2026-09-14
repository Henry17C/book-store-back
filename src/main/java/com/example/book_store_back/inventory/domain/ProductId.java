package com.example.book_store_back.inventory.domain;

import java.util.Objects;
// VO
public record ProductId(String value) {
    public ProductId {
        Objects.requireNonNull(value, "El id del producto no puede ser nulo.");
        if (value.isBlank()) {
            throw new IllegalArgumentException("El id del producto no puede estar en blanco.");
        }
    }
}
