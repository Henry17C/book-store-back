package com.example.book_store_back.orders.application.usecases;

import com.example.book_store_back.orders.application.dtos.MarkOrderAsAwaitingPaymentCommand;

public interface MarkOrderAsAwaitingPaymentUseCase {
    public void execute(MarkOrderAsAwaitingPaymentCommand command);
}
