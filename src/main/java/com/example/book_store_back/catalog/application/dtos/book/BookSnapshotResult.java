package com.example.book_store_back.catalog.application.dtos.book;

import java.math.BigDecimal;
import java.util.UUID;

public record BookSnapshotResult(
        // Usado para comunicarse con el modulo Order
        UUID id,
        BigDecimal price,
        String currency,
        String format) {

}
