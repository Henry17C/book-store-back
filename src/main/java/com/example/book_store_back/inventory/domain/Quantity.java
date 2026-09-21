package com.example.book_store_back.inventory.domain;

//VO
public record Quantity(int value) {
    public Quantity {
        if (value < 0) {
            throw new IllegalArgumentException("Quantity cannot be less than zero");
        }
    }

    public Quantity add(Quantity other) {
        return new Quantity(this.value + other.value);
    }

    public Quantity subtract(Quantity other) {
        if (this.value - other.value < 0) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        return new Quantity(this.value - other.value);
    }
}