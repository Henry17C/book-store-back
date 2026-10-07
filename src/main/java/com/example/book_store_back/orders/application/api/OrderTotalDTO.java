package com.example.book_store_back.orders.application.api;

import java.math.BigDecimal;

public record OrderTotalDTO(
    BigDecimal amount, 
    String currency
) {}
