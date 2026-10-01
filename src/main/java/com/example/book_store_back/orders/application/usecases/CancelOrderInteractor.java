package com.example.book_store_back.orders.application.usecases;

import java.util.UUID;

import com.example.book_store_back.orders.application.dtos.CancelOrderCommand;
import com.example.book_store_back.orders.application.ports.OrderRepository;
import com.example.book_store_back.orders.domain.Order;
import com.example.book_store_back.orders.domain.OrderId;

public class CancelOrderInteractor implements CancelOrderUseCase{
    
    private final OrderRepository orderRepository;

    public CancelOrderInteractor(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }
        public void execute (CancelOrderCommand command){
            OrderId orderId = new OrderId(UUID.fromString(command.orderId()));
            Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));
            order.cancel(command.reason());
            orderRepository.save(order);
        }

}
