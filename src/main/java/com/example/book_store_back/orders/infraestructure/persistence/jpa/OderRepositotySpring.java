package com.example.book_store_back.orders.infraestructure.persistence.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book_store_back.orders.infraestructure.persistence.entity.OrderEntity;

public interface OderRepositotySpring extends JpaRepository<OrderEntity, UUID> {

}
