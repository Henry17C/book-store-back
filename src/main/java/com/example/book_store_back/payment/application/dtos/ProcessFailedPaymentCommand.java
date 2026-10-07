package com.example.book_store_back.payment.application.dtos;

public record ProcessFailedPaymentCommand(String rawGatewayTransactionId) {
}