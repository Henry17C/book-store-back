package com.example.book_store_back.inventory.application.usescases;

import com.example.book_store_back.inventory.application.dtos.inventory.InventoryItemResult;
import com.example.book_store_back.inventory.application.dtos.inventory.PageResult;

public interface GetInventoryItemsPageUseCase {
    
    public PageResult<InventoryItemResult> execute(int page, int size)  ;
}
