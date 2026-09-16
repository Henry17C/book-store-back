package com.example.book_store_back.inventory.application.dtos;

import com.example.book_store_back.inventory.domain.ProductId;
import com.example.book_store_back.inventory.domain.Quantity;
public record CreateInventoryItemCommand(
    ProductId productId,
    Quantity initialStock
) {
    
}
