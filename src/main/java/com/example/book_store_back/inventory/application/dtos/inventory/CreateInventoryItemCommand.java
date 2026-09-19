package com.example.book_store_back.inventory.application.dtos.inventory;

import java.time.LocalDateTime;

import com.example.book_store_back.inventory.domain.ProductId;
import com.example.book_store_back.inventory.domain.Quantity;
public record CreateInventoryItemCommand(
    ProductId productId,
    String title,
    String isbn,
    LocalDateTime createdAt,
    Quantity initialStock
) {}