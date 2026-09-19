package com.example.book_store_back.inventory.infrastructure.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.book_store_back.inventory.application.dtos.inventory.InventoryItemResult;
import com.example.book_store_back.inventory.application.dtos.inventory.PageResult;
import com.example.book_store_back.inventory.application.dtos.stock.AddStockCommand;
import com.example.book_store_back.inventory.application.dtos.stock.RemoveStockCommand;
import com.example.book_store_back.inventory.application.usescases.AddStockUseCase;
import com.example.book_store_back.inventory.application.usescases.GetInventoryItemsPageUseCase;
import com.example.book_store_back.inventory.application.usescases.RemoveStockUseCase;
import com.example.book_store_back.inventory.domain.ProductId;
import com.example.book_store_back.inventory.domain.Quantity;
import com.example.book_store_back.inventory.infrastructure.api.dto.request.AddStockRequest;
import com.example.book_store_back.inventory.infrastructure.api.dto.request.RemoveStockRequest;
import com.example.book_store_back.inventory.infrastructure.api.dto.response.InventoryItemResponse;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final AddStockUseCase addStockUseCase;
    private final RemoveStockUseCase removeStockUseCase;
    private final GetInventoryItemsPageUseCase getInventoryItemsPageUseCase;

    public InventoryController(AddStockUseCase addStockUseCase, RemoveStockUseCase removeStockUseCase,
            GetInventoryItemsPageUseCase getInventoryItemsPageUseCase) {
        this.addStockUseCase = addStockUseCase;
        this.removeStockUseCase = removeStockUseCase;
        this.getInventoryItemsPageUseCase = getInventoryItemsPageUseCase;
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

    @GetMapping
    public ResponseEntity<PageResult<InventoryItemResponse>> getInventoryItemsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResult<InventoryItemResult> pageResult = getInventoryItemsPageUseCase.execute(page, size);

        List<InventoryItemResponse> contentResponse = pageResult.content().stream().map(c -> {
            return new InventoryItemResponse(
            c.id(),
            c.productId(),
            c.title(),
            c.isbn(),
            c.stock()
        );
    }).toList();

        PageResult<InventoryItemResponse> response = new PageResult<>(contentResponse, pageResult.currentPage(),
                pageResult.totalPages(), pageResult.totalElements());

        return ResponseEntity.ok(response);
    }

}