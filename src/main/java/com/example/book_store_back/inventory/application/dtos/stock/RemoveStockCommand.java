package com.example.book_store_back.inventory.application.dtos.stock;

import com.example.book_store_back.inventory.domain.ProductId;
import com.example.book_store_back.inventory.domain.Quantity;

public record RemoveStockCommand(
    ProductId productId,
    Quantity quantity
) {
    
}
