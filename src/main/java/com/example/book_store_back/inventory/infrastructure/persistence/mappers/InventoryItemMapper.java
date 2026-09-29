package com.example.book_store_back.inventory.infrastructure.persistence.mappers;

import java.util.List;

import com.example.book_store_back.inventory.domain.InventoryItem;
import com.example.book_store_back.inventory.domain.InventoryItemId;
import com.example.book_store_back.inventory.domain.ProductId;
import com.example.book_store_back.inventory.domain.Quantity;
import com.example.book_store_back.inventory.domain.Reservation;
import com.example.book_store_back.inventory.domain.StockMovement;
import com.example.book_store_back.inventory.infrastructure.persistence.entity.InventoryItemEntity;

public class InventoryItemMapper {

    public static InventoryItem toDomain(InventoryItemEntity entity) {
        InventoryItemId id = new InventoryItemId(entity.getId());
        ProductId productId = new ProductId(entity.getProductId());
        Quantity physicalStock = new Quantity(entity.getPhysicalStock());

        List<StockMovement> movements = entity.getMovements().stream()
                .map(StockMovementMapper::toDomain)
                .toList();

        List<Reservation> activeReservations = entity.getActiveReservations().stream()
                .map(ReservationMapper::toDomain)
                .toList();

        return new InventoryItem(id, productId, entity.getCreatedAt(), physicalStock, movements,
                activeReservations, entity.getVersion());
    }

    // Método para a GUARDAR POR PRIMERA VEZ (INSERT)
    public static InventoryItemEntity toEntity(InventoryItem domain) {
        InventoryItemEntity entity = new InventoryItemEntity();

        updateEntity(domain, entity);
        return entity;
    }

    // Método para ACTUALIZAR (UPDATE - Patrón Saga / Reserva)
    public static void updateEntity(InventoryItem domain, InventoryItemEntity entity) {
        entity.setId(domain.getId().value());
        entity.setProductId(domain.getProductId().value());
        entity.setPhysicalStock(domain.getPhysicalStock().value());
        entity.setCreatedAt(domain.getCreatedAt());

        // ¡CRÍTICO PARA LA CONCURRENCIA!
        entity.setVersion(domain.getVersion());

        /*
         * MANEJO SEGURO DE COLECCIONES EN HIBERNATE
         * 1. Limpiar la colección original de Hibernate (NO la reemplazar con = new
         * ArrayList).
         * 2. Añadir los elementos uno por uno.
         * Así Hibernate sabe exactamente qué borrar (orphanRemoval) y qué insertar.
         */

        entity.getMovements().clear();
        for (StockMovement movement : domain.getMovements()) {
            entity.getMovements().add(StockMovementMapper.toEntity(movement, entity));
        }

        entity.getActiveReservations().clear();
        for (Reservation reservation : domain.getActiveReservations()) {
            entity.getActiveReservations().add(ReservationMapper.toEntity(reservation, entity));
        }
    }
}