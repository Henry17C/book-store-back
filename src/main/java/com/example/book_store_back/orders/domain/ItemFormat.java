package com.example.book_store_back.orders.domain;

public enum ItemFormat {
    PHYSICAL,
    DIGITAL;

    public boolean requiresShipping() {
        return this == PHYSICAL;
    }
}