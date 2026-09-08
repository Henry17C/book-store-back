package com.example.book_store_back.identity.domain;

public record FullName(String value) {

    public FullName {
        if (value == null || value.trim().isBlank()) {
            throw new IllegalArgumentException("El nombre del usuario no puede estar vacío.");
        }

        if (value.length()>100){
            throw new IllegalArgumentException("El nombre del usuario es demasiado largo.");

        }
    }
}
