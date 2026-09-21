package com.example.book_store_back.inventory.application.dtos.inventory;

public record InventoryItemResult(
        String id,
        String productId,
        String title,
        String isbn,
        Integer stock) {
}