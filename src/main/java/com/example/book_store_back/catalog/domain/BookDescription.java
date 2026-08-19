package com.example.book_store_back.catalog.domain;

public record BookDescription(String value) {

    public BookDescription {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacia");
        }
        if (value.length()>2000) {
            throw  new IllegalArgumentException("La longitud de la descripción no puede superar los 2000 caracteres");
        }
    }
}
