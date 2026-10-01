package com.example.book_store_back.orders.application.ports;

import java.util.Optional;

import com.example.book_store_back.orders.domain.Order;
import com.example.book_store_back.orders.domain.OrderId;

public interface OrderRepository {
    public void save(Order order);
    public Optional<Order> findById(OrderId id);
    
}
