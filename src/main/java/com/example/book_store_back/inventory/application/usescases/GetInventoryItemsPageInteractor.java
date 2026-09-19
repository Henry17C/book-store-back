package com.example.book_store_back.inventory.application.usescases;

import java.util.List;

import com.example.book_store_back.inventory.application.dtos.inventory.InventoryItemResult;
import com.example.book_store_back.inventory.application.dtos.inventory.PageResult;
import com.example.book_store_back.inventory.application.ports.InventoryRepository;
import com.example.book_store_back.inventory.domain.InventoryItem;

public class GetInventoryItemsPageInteractor implements GetInventoryItemsPageUseCase {

    private final InventoryRepository inventoryRepository;

    public GetInventoryItemsPageInteractor(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public PageResult<InventoryItemResult> execute(int page, int size) {
        if (page < 0)
            page = 0;
        if (size <= 0 || size > 50)
            size = 20;

        PageResult<InventoryItem> domainPage = inventoryRepository.findAllPaged(page, size);

        List<InventoryItemResult> itemResults = domainPage.content().stream()
                .map(item -> new InventoryItemResult(
                        item.getId().value(),
                        item.getProductId().value(),
                        item.getTitle(),
                        item.getIsbn(),
                        item.getStock().value()))
                .toList();

        return new PageResult<>(
                itemResults,
                domainPage.currentPage(),
                domainPage.totalPages(),
                domainPage.totalElements());
    }
}