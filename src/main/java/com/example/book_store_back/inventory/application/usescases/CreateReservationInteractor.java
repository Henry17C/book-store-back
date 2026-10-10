package com.example.book_store_back.inventory.application.usescases;

import java.time.LocalDateTime;

import com.example.book_store_back.inventory.application.ports.InventoryRepository;
import com.example.book_store_back.inventory.domain.InventoryItem;
import com.example.book_store_back.inventory.domain.OrderId;
import com.example.book_store_back.inventory.domain.ProductId;
import com.example.book_store_back.inventory.domain.Quantity;
import com.example.book_store_back.shared.application.ports.DomainEventPublisher;
import com.example.book_store_back.shared.domain.events.OrderCreatedEvent;
import com.example.book_store_back.shared.domain.events.StockReservedEvent;
import com.example.book_store_back.shared.domain.events.StockReservedFailedEvent;

public class CreateReservationInteractor implements CreateReservationUseCase {

    private final InventoryRepository inventoryRepository;
    private final int reservationMinutes;
    private final DomainEventPublisher domainEventPublisher;

    public CreateReservationInteractor(
            InventoryRepository inventoryRepository,
            int reservationMinutes,
            DomainEventPublisher domainEventPublisher) {
        this.inventoryRepository = inventoryRepository;
        this.reservationMinutes = reservationMinutes;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public void execute(OrderCreatedEvent event) {

        OrderId orderId = new OrderId(event.orderId());
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(reservationMinutes);

        try {
            event.items().forEach(itemEvent -> {
                ProductId productId = new ProductId(itemEvent.bookId());
                Quantity quantityToReserve = new Quantity(itemEvent.quantity());

                InventoryItem inventoryItem = inventoryRepository.findByProductId(productId)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Inventario no encontrado para el producto: " + productId.value()));

                inventoryItem.reserve(orderId, quantityToReserve, expiresAt);

                inventoryRepository.save(inventoryItem);
            });
            domainEventPublisher.publish(new StockReservedEvent(orderId.value()));

        } catch (IllegalArgumentException e) {
            domainEventPublisher.publish(new StockReservedFailedEvent(orderId.value(), "PRODUCT_NOT_FOUND"));
            throw e;

        } catch (IllegalStateException e) {
            domainEventPublisher.publish(new StockReservedFailedEvent(orderId.value(), "OUT_OF_STOCK"));
            throw e;
        }
    }
}