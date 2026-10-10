package com.example.book_store_back.inventory.infrastructure.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "inventory_reservations")
@Entity
@Getter
@Setter
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Guardar el OrderId como un simple String en la BD
    @Column(name = "order_id", nullable = false, updatable = false)
    private String orderId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

        // Relación de muchos a uno con el Inventario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private InventoryItemEntity inventoryItem;

    protected ReservationEntity() {}
    
    public ReservationEntity(InventoryItemEntity inventoryItem, String orderId, Integer quantity, LocalDateTime expiresAt) {
        this.inventoryItem = inventoryItem;
        this.orderId = orderId;
        this.quantity = quantity;
        this.expiresAt = expiresAt;
    }
}
