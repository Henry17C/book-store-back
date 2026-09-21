package com.example.book_store_back.inventory.application.usescases;

import com.example.book_store_back.inventory.application.dtos.stock.RemoveStockCommand;

public interface RemoveStockUseCase {
    public void execute(RemoveStockCommand command);
}
