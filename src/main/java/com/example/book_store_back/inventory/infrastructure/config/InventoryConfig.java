package com.example.book_store_back.inventory.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.book_store_back.inventory.application.ports.InventoryRepository;
import com.example.book_store_back.inventory.application.usescases.AddStockInteractor;
import com.example.book_store_back.inventory.application.usescases.AddStockUseCase;
import com.example.book_store_back.inventory.application.usescases.CreateInventoryItemInteractor;
import com.example.book_store_back.inventory.application.usescases.CreateInventoryItemUseCase;
import com.example.book_store_back.inventory.application.usescases.GetInventoryItemsPageInteractor;
import com.example.book_store_back.inventory.application.usescases.GetInventoryItemsPageUseCase;
import com.example.book_store_back.inventory.application.usescases.RemoveStockInteractor;
import com.example.book_store_back.inventory.application.usescases.RemoveStockUseCase;

@Configuration
public class InventoryConfig {
    
    @Bean
    public AddStockUseCase addStockUseCase(InventoryRepository inventoryRepository){
        return new AddStockInteractor(inventoryRepository);
    }

    @Bean
    public CreateInventoryItemUseCase createInventoryItemUseCase(InventoryRepository inventoryRepository){
        return new CreateInventoryItemInteractor(inventoryRepository);
    }

    @Bean
    public GetInventoryItemsPageUseCase getInventoryItemsPageUseCase(InventoryRepository inventoryRepository){
        return new GetInventoryItemsPageInteractor(inventoryRepository);
    }

    @Bean
    public RemoveStockUseCase removeStockUseCase(InventoryRepository inventoryRepository){
        return new RemoveStockInteractor(inventoryRepository);
    }

}
