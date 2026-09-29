package com.example.book_store_back.inventory.infrastructure.persistence.mappers;

import com.example.book_store_back.inventory.domain.OrderId;
import com.example.book_store_back.inventory.domain.Quantity;
import com.example.book_store_back.inventory.domain.Reservation;
import com.example.book_store_back.inventory.infrastructure.persistence.entity.InventoryItemEntity;
import com.example.book_store_back.inventory.infrastructure.persistence.entity.ReservationEntity;

public class ReservationMapper {
    public static Reservation toDomain(ReservationEntity entity) {
        return new Reservation(
                new OrderId(entity.getOrderId()),
                new Quantity(entity.getQuantity()),
                entity.getExpiresAt());
    }
    
    public static ReservationEntity toEntity(Reservation domain, InventoryItemEntity parent) {
        return new ReservationEntity(
                parent,
                domain.orderId().value(),
                domain.quantity().value(),
                domain.expiresAt());
    }
}
