package com.example.book_store_back.orders.infraestructure.persistence.adapters;

import com.example.book_store_back.orders.application.ports.OrderRepository;
import com.example.book_store_back.orders.domain.Order;
import com.example.book_store_back.orders.infraestructure.persistence.entity.OrderEntity;
import com.example.book_store_back.orders.infraestructure.persistence.jpa.OderRepositotySpring;
import com.example.book_store_back.orders.infraestructure.persistence.mappers.OrderMapper;

public class OrderRepositoryAdapter implements OrderRepository {

    private final OderRepositotySpring oderRepositotySpring;

    public OrderRepositoryAdapter(OderRepositotySpring oderRepositotySpring) {
        this.oderRepositotySpring = oderRepositotySpring;
    }

    @Override
    public void save(Order order) {
        OrderEntity entity = OrderMapper.toEntity(order);
        oderRepositotySpring.save(entity);
    }
}
