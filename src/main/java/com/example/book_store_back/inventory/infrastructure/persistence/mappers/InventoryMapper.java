package com.example.book_store_back.inventory.infrastructure.persistence.mappers;

import java.util.List;

import com.example.book_store_back.inventory.domain.InventoryItem;
import com.example.book_store_back.inventory.domain.InventoryItemId;
import com.example.book_store_back.inventory.domain.ProductId;
import com.example.book_store_back.inventory.domain.Quantity;
import com.example.book_store_back.inventory.domain.StockMovement;
import com.example.book_store_back.inventory.infrastructure.persistence.entity.InventoryItemEntity;
import com.example.book_store_back.inventory.infrastructure.persistence.entity.StockMovementEntity;

public class InventoryMapper {

    public static InventoryItem toDomain(InventoryItemEntity entity) {
        InventoryItemId id = new InventoryItemId(entity.getId());
        ProductId productId = new ProductId(entity.getProductId());
        Quantity stock = new Quantity(entity.getStock());

        List<StockMovement> movements = entity.getMovements().stream()
            .map(StockMovementMapper::toDomain)
            .toList();

        return new InventoryItem(id, productId, stock, movements);
    }

    public static InventoryItemEntity toEntity(InventoryItem domain) {
        InventoryItemEntity inventoryEntity = new InventoryItemEntity();
        inventoryEntity.setId(domain.getId().value());
        inventoryEntity.setProductId(domain.getProductId().value());
        inventoryEntity.setStock(domain.getStock().value());

        // Mapear y asegurar la relación bidireccional con el padre usando setters
        List<StockMovementEntity> movementEntities = domain.getMovements().stream()
            .map(movement -> StockMovementMapper.toEntity(movement, inventoryEntity))
            .toList();

        inventoryEntity.setMovements(movementEntities);

        return inventoryEntity;
    }
}
