package com.example.book_store_back.identity.domain;

public record Password(String hash) {
    
    public Password {
        if (hash != null && hash.trim().isEmpty()) {
            throw new IllegalArgumentException("El hash de la contraseña no puede estar vacío.");
        }
    }
}
