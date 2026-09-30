package com.example.book_store_back.inventory.application.usescases;

import com.example.book_store_back.shared.domain.events.OrderCreatedEvent;

public interface CreateReservationUseCase {
    public void execute(OrderCreatedEvent event);
}
