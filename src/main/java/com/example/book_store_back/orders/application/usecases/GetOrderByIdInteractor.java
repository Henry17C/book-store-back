package com.example.book_store_back.orders.application.usecases;

import java.util.List;
import java.util.UUID;

import com.example.book_store_back.orders.application.dtos.OrderDetailsResult;
import com.example.book_store_back.orders.application.dtos.OrderItemResult;
import com.example.book_store_back.orders.application.dtos.OrderItemResult.UnitPriceResult;
import com.example.book_store_back.orders.application.ports.OrderRepository;
import com.example.book_store_back.orders.domain.Order;
import com.example.book_store_back.orders.domain.OrderId;

public class GetOrderByIdInteractor implements GetOrderByIdUseCase {
    private final OrderRepository orderRepository;

    public GetOrderByIdInteractor(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDetailsResult execute(String orderId) {
        OrderId id = new OrderId(UUID.fromString(orderId));
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Orden no encontrada con ID: " + orderId);
        });

        List<OrderItemResult> itemsResult = order.getOrderItems().stream().map(item -> {
            UnitPriceResult price = new UnitPriceResult(item.getUnitPrice().amount(), item.getUnitPrice().currency());
            return new OrderItemResult(item.getBookId().value(), item.getQuantity().value(), price,
                    item.calculateSubtotal());
        }).toList();

        OrderDetailsResult result = new OrderDetailsResult(
                order.getId().value().toString(),
                order.getStatus().toString(),
                order.calculateTarifa0(),
                order.calculateTarifa15(),
                order.calculateIva15(),
                order.calculateTotal(),
                order.getCancellationReason(),
                itemsResult);

        return result;
    }
}
