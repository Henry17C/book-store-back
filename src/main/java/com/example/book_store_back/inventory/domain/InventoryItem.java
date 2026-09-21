package com.example.book_store_back.inventory.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

//Aggregate
public class InventoryItem {
    private final InventoryItemId id;
    private final ProductId productId;
    private final String title;
    private final String isbn;
    private final LocalDateTime createdAt;
    private Quantity stock;
    private final List<StockMovement> movements;

    public InventoryItem(InventoryItemId id, ProductId productId, String title, String isbn, LocalDateTime createdAt,
            Quantity stock, List<StockMovement> movements) {
        this.id = Objects.requireNonNull(id, "El id del inventario no puede ser nulo");
        this.productId = Objects.requireNonNull(productId, "El id del producto no puede ser nulo.");
        this.stock = Objects.requireNonNull(stock, "El stock no puede ser nulo.");
        this.movements = Objects.requireNonNull(movements, "La lista de movimientos no puede ser nula.");
        this.title = Objects.requireNonNull(title, "El título no puede ser nulo.");
        this.isbn = Objects.requireNonNull(isbn, "El ISBN no puede ser nulo.");
        this.createdAt = Objects.requireNonNull(createdAt, "El fecha de creación no puede ser nula.");

    }

    // Factory para crear un nuevo inventario desde cero (dar de alta un producto)
    public static InventoryItem create(ProductId productId, String title, String isbn, LocalDateTime createdAt,
            Quantity initialStock) {
        InventoryItemId newId = InventoryItemId.generate();
        List<StockMovement> initialMovements = new java.util.ArrayList<>();

        if (initialStock.value() > 0) {
            initialMovements.add(new StockMovement(
                    StockMovementId.generate(),
                    initialStock,
                    MovementType.ENTRY,
                    LocalDateTime.now()));
        }

        return new InventoryItem(newId, productId, title, isbn, createdAt, initialStock, initialMovements);
    }

    public void addStock(Quantity amount) {
        this.stock = this.stock.add(amount);

        this.movements.add(new StockMovement(
                StockMovementId.generate(),
                amount,
                MovementType.ENTRY,
                LocalDateTime.now()));
    }

    public void removeStock(Quantity amount) {
        this.stock = this.stock.subtract(amount);
        this.movements.add(new StockMovement(
                StockMovementId.generate(),
                amount,
                MovementType.EXIT,
                LocalDateTime.now()));
    }

    public InventoryItemId getId() {
        return id;
    }

    public ProductId getProductId() {
        return productId;
    }

    public Quantity getStock() {
        return stock;
    }

    public List<StockMovement> getMovements() {
        return Collections.unmodifiableList(movements);
    }

    public String getTitle() {
        return this.title;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

}
