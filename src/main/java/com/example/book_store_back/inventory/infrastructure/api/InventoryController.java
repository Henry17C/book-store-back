package com.example.book_store_back.inventory.infrastructure.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.book_store_back.inventory.application.dtos.AddStockCommand;
import com.example.book_store_back.inventory.application.dtos.RemoveStockCommand;
import com.example.book_store_back.inventory.application.usescases.AddStockUseCase;
import com.example.book_store_back.inventory.application.usescases.RemoveStockUseCase;
import com.example.book_store_back.inventory.domain.ProductId;
import com.example.book_store_back.inventory.domain.Quantity;
import com.example.book_store_back.inventory.infrastructure.api.dto.request.AddStockRequest;
import com.example.book_store_back.inventory.infrastructure.api.dto.request.RemoveStockRequest;



@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final AddStockUseCase addStockUseCase;
    private final RemoveStockUseCase removeStockUseCase;

    public InventoryController(AddStockUseCase addStockUseCase, RemoveStockUseCase removeStockUseCase) {
        this.addStockUseCase = addStockUseCase;
        this.removeStockUseCase = removeStockUseCase;
    }

    @PostMapping("/{productId}/add")
    public ResponseEntity<Void> addStock(
            @PathVariable String productId,
            @RequestBody AddStockRequest request) {
        AddStockCommand command = new AddStockCommand(
                new ProductId(productId),
                new Quantity(request.quantity()));

        addStockUseCase.execute(command);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/remove")
    public ResponseEntity<Void> removeStock(
            @PathVariable String id,
            @RequestBody RemoveStockRequest request) {

        RemoveStockCommand command = new RemoveStockCommand(
                new ProductId(id),
                new Quantity(request.quantity()));
        removeStockUseCase.execute(command);
        
        return ResponseEntity.ok().build();
    }

}