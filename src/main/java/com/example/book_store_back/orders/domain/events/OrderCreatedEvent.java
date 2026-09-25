package com.example.book_store_back.orders.domain.events;

import java.util.List;

public record OrderCreatedEvent(
                String orderId,
                List<OrderItemEvent> items) {
        public record OrderItemEvent(
                        String bookId,
                        int quantity) {

        }
}
