package com.example.book_store_back.inventory.application.usescases;

import org.springframework.transaction.annotation.Transactional;

import com.example.book_store_back.inventory.application.dtos.RemoveStockCommand;
import com.example.book_store_back.inventory.application.ports.InventoryRepository;
import com.example.book_store_back.inventory.domain.InventoryItem;
import com.example.book_store_back.inventory.domain.ProductId;
import com.example.book_store_back.inventory.domain.Quantity;

@Transactional //Segurar la atomicidad
public class RemoveStockInteractor implements  RemoveStockUseCase{

    private final InventoryRepository inventoryRepository;

    public RemoveStockInteractor(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void execute(RemoveStockCommand command) {
        ProductId productId = command.productId();
        Quantity stock = command.quantity();

        InventoryItem inventoryItem = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Producto con ID " + productId + " no existe en inventario"));

                inventoryItem.removeStock(stock);

        inventoryRepository.save(inventoryItem);
    }
}