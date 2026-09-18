package com.example.book_store_back.inventory.infrastructure.api.dto.response;

public record InventoryItemResponse(
    String id,
    String productId,
    Integer stock
) {}