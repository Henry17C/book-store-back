package com.example.book_store_back.orders.application.usecases;

import com.example.book_store_back.orders.application.dtos.MarkOrderAsPaidCommand;
import com.example.book_store_back.orders.application.ports.OrderRepository;
import com.example.book_store_back.orders.domain.OrderId;

import java.util.UUID;

import com.example.book_store_back.orders.domain.Order;

public class MarkOrderAsPaidInteractor implements MarkOrderAsPaidUseCase {

    private final OrderRepository orderRepository;

    public MarkOrderAsPaidInteractor(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override 
    public void execute(MarkOrderAsPaidCommand command) {

        OrderId orderId = new OrderId(UUID.fromString(command.orderId()));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            return new IllegalArgumentException("No se encontro la order con el id: " + orderId.value().toString());
        });
        order.markAsPaid();

        orderRepository.save(order);

    }

}
