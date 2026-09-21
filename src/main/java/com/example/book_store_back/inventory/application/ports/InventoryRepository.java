package com.example.book_store_back.inventory.application.ports;

import java.util.Optional;

import com.example.book_store_back.inventory.application.dtos.inventory.PageResult;
import com.example.book_store_back.inventory.domain.InventoryItem;
import com.example.book_store_back.inventory.domain.ProductId;

public interface InventoryRepository {
    public void save(InventoryItem inventoryItem);
    boolean existsByProductId(ProductId productId);
    Optional<InventoryItem> findByProductId(ProductId productId);
    PageResult<InventoryItem> findAllPaged(int page, int size);
}
