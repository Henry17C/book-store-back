package com.example.book_store_back.orders.application.usecases;

import java.util.UUID;

import com.example.book_store_back.orders.application.dtos.MarkOrderAsAwaitingPaymentCommand;
import com.example.book_store_back.orders.application.ports.OrderRepository;
import com.example.book_store_back.orders.domain.Order;
import com.example.book_store_back.orders.domain.OrderId;

public class MarkOrderAsAwaitingPaymentInteractor implements MarkOrderAsAwaitingPaymentUseCase {

    private final OrderRepository orderRepository;

    public MarkOrderAsAwaitingPaymentInteractor(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void execute(MarkOrderAsAwaitingPaymentCommand command) {
        OrderId orderId = new OrderId(UUID.fromString(command.orderId()));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        order.markAsReserved();
        orderRepository.save(order);
    }
}
