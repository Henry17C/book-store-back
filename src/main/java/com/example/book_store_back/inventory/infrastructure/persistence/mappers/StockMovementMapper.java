package com.example.book_store_back.inventory.infrastructure.persistence.mappers;

import java.time.LocalDateTime;

import com.example.book_store_back.inventory.domain.MovementType;
import com.example.book_store_back.inventory.domain.Quantity;
import com.example.book_store_back.inventory.domain.StockMovement;
import com.example.book_store_back.inventory.domain.StockMovementId;
import com.example.book_store_back.inventory.infrastructure.persistence.entity.InventoryItemEntity;
import com.example.book_store_back.inventory.infrastructure.persistence.entity.StockMovementEntity;

public class StockMovementMapper {

    public static StockMovement toDomain(StockMovementEntity entity) {
        StockMovementId id = new StockMovementId(entity.getId());
        Quantity quantity = new Quantity(entity.getQuantity());
        
        MovementType type = entity.getType();
        
        LocalDateTime createdAt = entity.getCreatedAt();

        return new StockMovement(id, quantity, type, createdAt);
    }

    public static StockMovementEntity toEntity(StockMovement domain, InventoryItemEntity inventoryItemEntity) {
        StockMovementEntity entity = new StockMovementEntity();
        entity.setId(domain.getId().value());
        entity.setQuantity(domain.getQuantity().value());
        
        entity.setType(domain.getType());
        
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setInventoryItem(inventoryItemEntity); // Vincula la relación con el padre

        return entity;
    }
}