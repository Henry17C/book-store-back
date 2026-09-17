package com.example.book_store_back.inventory.application.usescases;

import org.springframework.transaction.annotation.Transactional;

import com.example.book_store_back.inventory.application.dtos.AddStockCommand;
import com.example.book_store_back.inventory.application.ports.InventoryRepository;
import com.example.book_store_back.inventory.domain.InventoryItem;
import com.example.book_store_back.inventory.domain.ProductId;
import com.example.book_store_back.inventory.domain.Quantity;

@Transactional //Segurar la atomicidad
public class AddStockInteractor implements AddStockUseCase {

    private final InventoryRepository inventoryRepository;

    public AddStockInteractor(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void execute(AddStockCommand command) {
        ProductId productId = command.productId();
        Quantity stock = command.quantity();

        InventoryItem inventoryItem = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Producto con ID " + productId + " no existe en inventario"));

                inventoryItem.addStock(stock);

        inventoryRepository.save(inventoryItem);
    }
}