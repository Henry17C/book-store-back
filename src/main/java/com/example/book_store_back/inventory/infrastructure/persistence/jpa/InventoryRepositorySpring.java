package com.example.book_store_back.inventory.infrastructure.persistence.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book_store_back.inventory.infrastructure.persistence.entity.InventoryItemEntity;

public interface InventoryRepositorySpring extends JpaRepository<InventoryItemEntity, String> {
    boolean existsByProductId(String productId);

    Optional<InventoryItemEntity> findByProductId(String productId);

}
