package com.example.book_store_back.orders.application.ports;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.example.book_store_back.orders.domain.ItemFormat;

public interface CatalogGateway {
    // Retorna los datos reales y seguros del libro
    public record BookSnapshotData(UUID bookId, BigDecimal price, String currency, ItemFormat format) {
    }

    List<BookSnapshotData> getBooksDetails(List<UUID> bookIds);
}
