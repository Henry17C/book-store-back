package com.example.book_store_back.orders.domain;

import java.math.BigDecimal;
import java.util.Objects;

//Entity
public class OrderItem {
    private final BookId bookId;
    private final PriceSnapshot unitPrice;
    private final Quantity quantity;
    private final ItemFormat format;

    public OrderItem(BookId bookId, ItemFormat format, PriceSnapshot unitPrice, Quantity quantity) {
        this.bookId = Objects.requireNonNull(bookId, "El id del libro no puede ser nulo.");
        this.unitPrice = Objects.requireNonNull(unitPrice, "El precio por unidad no puede ser nulo.");
        this.quantity = Objects.requireNonNull(quantity, "La cantidad no puede ser nula.");
        this.format = Objects.requireNonNull(format, "El formato no puede ser nulo.");
    }

    // Comportamiento del dominio: calcular el subtotal
    public BigDecimal calculateSubtotal() {
        return unitPrice.amount().multiply(quantity.value());
    }

    // Getters
    public BookId getBookId() {
        return this.bookId;
    }

    public PriceSnapshot getUnitPrice() {
        return this.unitPrice;
    }

    public Quantity getQuantity() {
        return this.quantity;
    }
    public ItemFormat getItemFormat() {
        return this.format;
    }

}