package com.example.book_store_back.orders.application.dtos;

public record MarkOrderAsAwaitingPaymentCommand(
        String orderId) {
}
