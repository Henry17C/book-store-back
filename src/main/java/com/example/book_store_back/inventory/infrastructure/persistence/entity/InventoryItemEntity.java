package com.example.book_store_back.inventory.infrastructure.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "inventory_items")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItemEntity {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "product_id", length = 36, nullable = false, unique = true)
    private String productId;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    // Relación de 1 a Muchos con los movimientos.
    @OneToMany(mappedBy = "inventoryItem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StockMovementEntity> movements = new ArrayList<>();

}
