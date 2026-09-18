package com.example.book_store_back.inventory.infrastructure.persistence.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.book_store_back.inventory.application.dtos.inventory.PageResult;
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

    @Override
    public PageResult<InventoryItem> findAllPaged(int page, int size) {

        Pageable pageable = PageRequest.of(page, size /*, Sort.by("createdAt").descending()*/);
        Page<InventoryItemEntity> entityPage = inventoryRepositorySpring.findAll(pageable);

        List<InventoryItem> items = entityPage.getContent().stream()
                .map(InventoryMapper::toDomain)
                .toList();

        return new PageResult<>(
                items,
                entityPage.getNumber(),
                entityPage.getTotalPages(),
                entityPage.getTotalElements()
        );
    }

}