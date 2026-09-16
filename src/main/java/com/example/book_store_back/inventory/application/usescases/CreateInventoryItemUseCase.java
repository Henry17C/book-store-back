package com.example.book_store_back.inventory.application.usescases;

import com.example.book_store_back.inventory.application.dtos.CreateInventoryItemCommand;

public interface RegisterInventoryItemUseCase {
    void execute(CreateInventoryItemCommand command);
}
