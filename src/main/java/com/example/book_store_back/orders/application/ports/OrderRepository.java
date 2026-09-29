package com.example.book_store_back.orders.application.ports;

import com.example.book_store_back.orders.domain.Order;

public interface OrderRepository {
    public void save(Order order);
    
}
