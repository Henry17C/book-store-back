package com.example.book_store_back.inventory.domain;

import java.time.LocalDateTime;

public record Reservation(
        OrderId orderId, 
        Quantity quantity, 
        LocalDateTime expiresAt
) {
    public boolean isExpired(LocalDateTime currentTime) {
        return currentTime.isAfter(expiresAt);
    }
}