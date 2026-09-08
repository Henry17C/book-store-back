package com.example.book_store_back.identity.domain;

public record UserId(String value) {
    public UserId {
        // Firebase UIDs son strings de 28 caracteres.
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El ID de usuario no puede estar vacío");
        }
    }
}