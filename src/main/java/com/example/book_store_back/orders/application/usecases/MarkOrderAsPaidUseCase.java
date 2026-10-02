package com.example.book_store_back.orders.application.usecases;

import com.example.book_store_back.orders.application.dtos.MarkOrderAsPaidCommand;

public interface MarkOrderAsPaidUseCase {
    public void execute (MarkOrderAsPaidCommand command);
}
