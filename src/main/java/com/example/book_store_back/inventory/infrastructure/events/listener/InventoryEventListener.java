package com.example.book_store_back.inventory.infrastructure.events.listener;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.book_store_back.inventory.application.dtos.inventory.CreateInventoryItemCommand;
import com.example.book_store_back.inventory.application.usescases.CreateInventoryItemUseCase;
import com.example.book_store_back.inventory.domain.ProductId;
import com.example.book_store_back.inventory.domain.Quantity;
import com.example.book_store_back.inventory.domain.event.BookCreatedEvent;

@Component
public class InventoryEventListener {

    private final CreateInventoryItemUseCase createInventoryItemUseCase;

    public InventoryEventListener(CreateInventoryItemUseCase createInventoryItemUseCase) {
        this.createInventoryItemUseCase = createInventoryItemUseCase;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onBookCreated(BookCreatedEvent event) {
        CreateInventoryItemCommand command = new CreateInventoryItemCommand(
                new ProductId(event.productId()),
                event.title(),
                event.isbn(),
                event.createdAt(),
                new Quantity(0));

        createInventoryItemUseCase.execute(command);
    }
}