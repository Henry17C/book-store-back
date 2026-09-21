package com.example.book_store_back.inventory.application.usescases;

import com.example.book_store_back.inventory.application.dtos.stock.AddStockCommand;

public interface AddStockUseCase {
    public void execute(AddStockCommand command);
}
