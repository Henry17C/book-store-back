package com.example.book_store_back.payment.application.dtos;

public record PaymentIntentResult(
        String gatewayTransactionId,
        String clientSecret) {
}
