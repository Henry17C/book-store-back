package com.example.book_store_back.shared.domain.events;

import java.util.List;

public record OrderCreatedEvent(
                String orderId,
                List<OrderItemEvent> items) {
        public record OrderItemEvent(
                        String bookId,
                        int quantity) {

        }
}
