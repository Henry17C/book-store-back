package com.example.book_store_back.shared.domain.events;

public record PaymentApprovedEvent(
    String orderId,
    String paymentId
) {
    
}
