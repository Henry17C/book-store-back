package com.example.book_store_back.payment.infrastructure.api.dto;

public record PaymentIntentResponse(
        String gatewayTransactionId,
        String clientSecret) {

}
