package com.example.book_store_back.catalog.application.api;

import java.math.BigDecimal;
import java.util.UUID;

public record BookSnapshotDTO(
        UUID id,
        BigDecimal priceAmount,
        String priceCurrency,
        String format) {
}