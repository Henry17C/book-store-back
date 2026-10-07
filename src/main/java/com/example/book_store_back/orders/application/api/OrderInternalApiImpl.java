package com.example.book_store_back.orders.application.api;

import java.util.UUID;

import com.example.book_store_back.orders.application.ports.OrderRepository;
import com.example.book_store_back.orders.domain.Order;
import com.example.book_store_back.orders.domain.OrderId;

public class OrderInternalApiImpl implements OrderInternalApi {

    private final OrderRepository orderRepository;

    public OrderInternalApiImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderTotalDTO getOrderTotal(String orderId) {
        OrderId id = new OrderId(UUID.fromString(orderId));

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        return new OrderTotalDTO(
                order.calculateTotal(),
                "USD"
        );
    }
}