package com.example.book_store_back.shared.domain.events;

public record StockReservedFailedEvent(
    String orderId,
    String reason
) {
    
}
