package com.example.book_store_back.orders.application.dtos;

import java.math.BigDecimal;

public record OrderItemResult(
    String bookId,
    int quantity,
    UnitPriceResult price,
    BigDecimal subtotal
) {
    public record UnitPriceResult (
        BigDecimal amount,
        String currency
    ){

    }
}
