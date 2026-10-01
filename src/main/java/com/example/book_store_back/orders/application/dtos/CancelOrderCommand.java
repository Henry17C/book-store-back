package com.example.book_store_back.orders.application.dtos;

public record CancelOrderCommand(
        String orderId,
        String reason) {

}
