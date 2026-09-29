package com.example.book_store_back.inventory.infrastructure.persistence.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.book_store_back.inventory.application.dtos.inventory.PageResult;
import com.example.book_store_back.inventory.application.ports.InventoryRepository;
import com.example.book_store_back.inventory.domain.InventoryItem;
import com.example.book_store_back.inventory.domain.ProductId;
import com.example.book_store_back.inventory.infrastructure.persistence.entity.InventoryItemEntity;
import com.example.book_store_back.inventory.infrastructure.persistence.jpa.InventoryRepositorySpring;
import com.example.book_store_back.inventory.infrastructure.persistence.mappers.InventoryItemMapper;

@Repository
public class InventoryRepositoryAdapter implements InventoryRepository {

    private final InventoryRepositorySpring inventoryRepositorySpring;

    public InventoryRepositoryAdapter(InventoryRepositorySpring inventoryRepositorySpring) {
        this.inventoryRepositorySpring = inventoryRepositorySpring;
    }

    @Override
    public void save(InventoryItem inventoryItem) {
        // 1. Buscar si la entidad ya existe en la BD usando el ID del Agregado
        Optional<InventoryItemEntity> existingEntityOpt = inventoryRepositorySpring
                .findById(inventoryItem.getId().value());

        if (existingEntityOpt.isPresent()) {
            // 2. Si existe (ES UN UPDATE):
            // Tomar la entidad "viva" de Hibernate y solo le actualizar los valores
            // internos.
            InventoryItemEntity entityToUpdate = existingEntityOpt.get();
            InventoryItemMapper.updateEntity(inventoryItem, entityToUpdate);
            inventoryRepositorySpring.save(entityToUpdate);
        } else {
            // 3. Si no existe (ES UN INSERT POR PRIMERA VEZ):
            InventoryItemEntity newEntity = InventoryItemMapper.toEntity(inventoryItem);
            inventoryRepositorySpring.save(newEntity);
        }
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
                .map(InventoryItemMapper::toDomain);
    }

    @Override
    public PageResult<InventoryItem> findAllPaged(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<InventoryItemEntity> entityPage = inventoryRepositorySpring.findAll(pageable);

        List<InventoryItem> items = entityPage.getContent().stream()
                .map(InventoryItemMapper::toDomain)
                .toList();

        return new PageResult<>(
                items,
                entityPage.getNumber(),
                entityPage.getTotalPages(),
                entityPage.getTotalElements());
    }

}