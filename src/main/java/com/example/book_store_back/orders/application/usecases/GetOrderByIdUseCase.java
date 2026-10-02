package com.example.book_store_back.orders.application.usecases;

import com.example.book_store_back.orders.application.dtos.OrderDetailsResult;

public interface GetOrderByIdUseCase {
    public OrderDetailsResult execute(String orderId);
}
