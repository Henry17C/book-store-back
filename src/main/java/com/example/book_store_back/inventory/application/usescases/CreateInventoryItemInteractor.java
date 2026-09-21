package com.example.book_store_back.inventory.application.usescases;

import com.example.book_store_back.inventory.application.dtos.inventory.CreateInventoryItemCommand;
import com.example.book_store_back.inventory.application.ports.InventoryRepository;
import com.example.book_store_back.inventory.domain.InventoryItem;
import com.example.book_store_back.inventory.domain.Quantity;

public class CreateInventoryItemInteractor implements CreateInventoryItemUseCase {


    private final InventoryRepository inventoryRepository;

    public CreateInventoryItemInteractor(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void execute(CreateInventoryItemCommand command) {

        if (command == null) {
            throw new IllegalArgumentException("El comando de inventario no puede ser nulo.");
        }

        Quantity quantity = command.initialStock();

        if (inventoryRepository.existsByProductId(command.productId())) {
            throw new IllegalArgumentException(
                    "Ya existe un registro de inventario para el producto con ID: " + command.productId().value());
        }
        
        InventoryItem inventoryItem = InventoryItem.
        create(command.productId(),command.title(), command.isbn(),command.createdAt(), command.initialStock());
        inventoryRepository.save(inventoryItem);

    }

}
