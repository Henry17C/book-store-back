package com.example.book_store_back.catalog.application.dtos.book;

import java.math.BigDecimal;

public record MoneyResponse(BigDecimal amount, String currency) {}