package com.example.book_store_back.inventory.infrastructure.persistence.adapters;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.book_store_back.inventory.application.ports.InventoryRepository;
import com.example.book_store_back.inventory.domain.InventoryItem;
import com.example.book_store_back.inventory.domain.ProductId;
import com.example.book_store_back.inventory.infrastructure.persistence.entity.InventoryItemEntity;
import com.example.book_store_back.inventory.infrastructure.persistence.jpa.InventoryRepositorySpring;
import com.example.book_store_back.inventory.infrastructure.persistence.mappers.InventoryMapper;

@Repository
public class InventoryRepositoryAdapter implements InventoryRepository {

    private final InventoryRepositorySpring inventoryRepositorySpring;

    public InventoryRepositoryAdapter(InventoryRepositorySpring inventoryRepositorySpring) {
        this.inventoryRepositorySpring = inventoryRepositorySpring;
    }

    @Override
    public void save(InventoryItem inventoryItem) {
        InventoryItemEntity entity = InventoryMapper.toEntity(inventoryItem);
        inventoryRepositorySpring.save(entity);
    }

    @Override
    public boolean existsByProductId(ProductId productId) {
        String id = productId.value();
        return inventoryRepositorySpring.existsByProductId(id);
    }

    @Override
    public Optional<InventoryItem> findByProductId(ProductId productId) {
        String id = productId.value();
        return inventoryRepositorySpring.findByProductId(id)
                .map(InventoryMapper::toDomain);
    }
}