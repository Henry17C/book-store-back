package com.example.book_store_back.inventory.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//Aggregate
public class InventoryItem {
    private final InventoryItemId id;
    private final ProductId productId;
    private final LocalDateTime createdAt;
    private Quantity physicalStock;
    private final List<StockMovement> movements;

    private Long version;
    private final List<Reservation> activeReservations;

    public InventoryItem(InventoryItemId id, ProductId productId, LocalDateTime createdAt,
            Quantity physicalStock, List<StockMovement> movements, List<Reservation> activeReservations, Long version) {
        this.id = Objects.requireNonNull(id, "El id del inventario no puede ser nulo");
        this.productId = Objects.requireNonNull(productId, "El id del producto no puede ser nulo.");
        this.physicalStock = Objects.requireNonNull(physicalStock, "El stock no puede ser nulo.");
        Objects.requireNonNull(movements, "La lista de movimientos no puede ser nula.");
        this.movements = new ArrayList<>(movements);
        this.createdAt = Objects.requireNonNull(createdAt, "El fecha de creación no puede ser nula.");
        this.version = version;
        this.activeReservations = new ArrayList<>(activeReservations != null ? activeReservations : List.of());
    }

    // Factory para crear un nuevo inventario desde cero (dar de alta un producto)
    public static InventoryItem create(ProductId productId, Quantity initialStock) {
        InventoryItemId newId = InventoryItemId.generate();
        LocalDateTime now = LocalDateTime.now();
        List<StockMovement> initialMovements = new java.util.ArrayList<>();

        if (initialStock.value() > 0) {

            initialMovements.add(new StockMovement(
                    StockMovementId.generate(),
                    initialStock,
                    MovementType.ENTRY,
                    now));
        }

        return new InventoryItem(newId, productId, now, initialStock, initialMovements, new ArrayList<>(), null);
    }

    // --- REGLAS DE NEGOCIO Y SAGA ---

    public void addPhysicalStock(Quantity amount) {
        this.physicalStock = this.physicalStock.add(amount);
        this.movements.add(new StockMovement(
                StockMovementId.generate(),
                amount,
                MovementType.ENTRY,
                LocalDateTime.now()));
    }

    public void removePhysicalStock(Quantity amount) {
        this.physicalStock = this.physicalStock.subtract(amount);
        this.movements.add(new StockMovement(
                StockMovementId.generate(),
                amount,
                MovementType.EXIT,
                LocalDateTime.now()));
    }

    public Quantity getAvailableStock() {
        int reservedAmount = activeReservations.stream()
                .mapToInt(r -> r.quantity().value())
                .sum();
        return new Quantity(physicalStock.value() - reservedAmount);
    }

    private Optional<Reservation> findReservation(OrderId orderId) {
        return activeReservations.stream()
                .filter(r -> r.orderId().equals(orderId))
                .findFirst();
    }

    public void cancelReservation(OrderId orderId) {
        findReservation(orderId).ifPresent(this.activeReservations::remove);
    }

    // Paso 1 de la Saga: El Listener pide reservar
    public void reserve(OrderId orderId, Quantity quantityToReserve, LocalDateTime expiresAt) {
        if (getAvailableStock().value() < quantityToReserve.value()) {
            throw new IllegalStateException("Stock insuficiente para el producto: " + productId.value());
        }

        // Evitar duplicar reservas si el evento llega dos veces
        boolean alreadyReserved = activeReservations.stream().anyMatch(r -> r.orderId().equals(orderId));
        if (!alreadyReserved) {
            this.activeReservations.add(new Reservation(orderId, quantityToReserve, expiresAt));
        }
    }

    // Paso 2 de la Saga: Se confirma el pago, ahora SÍ quita el stock físico
    public void confirmReservationAndRemoveStock(OrderId orderId) {
        Reservation reservation = findReservation(orderId)
                .orElseThrow(() -> new IllegalStateException("No hay reserva activa para la orden " + orderId));

        // Aplicar la lógica original de descuento físico

        this.removePhysicalStock(reservation.quantity());
        // Eliminar la reserva temporal
        this.activeReservations.remove(reservation);

    }

    // Getters

    public InventoryItemId getId() {
        return id;
    }

    public Quantity getPhysicalStock() {
        return physicalStock;
    }


    public ProductId getProductId() {
        return productId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public List<StockMovement> getMovements() {
        return Collections.unmodifiableList(movements);
    }

    public Long getVersion() {
        return version;
    }

    public List<Reservation> getActiveReservations() {
        return Collections.unmodifiableList(activeReservations);
    }

}
