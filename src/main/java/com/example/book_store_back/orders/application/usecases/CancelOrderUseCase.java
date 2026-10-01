package com.example.book_store_back.orders.application.usecases;

import com.example.book_store_back.orders.application.dtos.CancelOrderCommand;

public interface CancelOrderUseCase {
    public void execute (CancelOrderCommand command);
}
