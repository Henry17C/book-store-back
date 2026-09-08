package com.example.book_store_back.identity.domain;

public record Email(String value) {
    private static final String REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public Email {
        if (value == null || !value.matches(REGEX)) {
            throw new IllegalArgumentException("El formato del correo es inválido");
        }
    }
}