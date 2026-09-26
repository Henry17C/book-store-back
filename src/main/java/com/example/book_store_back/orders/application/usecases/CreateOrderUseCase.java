package com.example.book_store_back.orders.application.usecases;

import java.util.UUID;

import com.example.book_store_back.orders.application.dtos.CreateOrderCommand;
public interface CreateOrderUseCase {
    UUID execute(CreateOrderCommand command);
}
