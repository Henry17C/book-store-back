package com.example.book_store_back.orders.domain;

import java.util.Objects;

public record BookId(String value) {
    public BookId {
        Objects.requireNonNull(value, "El id del libro no puede ser nulo.");
    }
}
