package com.example.book_store_back.catalog.infrastructure.api.dto.response.book;
import java.math.BigDecimal;

public record MoneyResponse(
    BigDecimal amount,
    String currency
) {
    
}
