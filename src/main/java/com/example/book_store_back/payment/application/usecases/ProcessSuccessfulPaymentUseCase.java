package com.example.book_store_back.payment.application.usecases;

import com.example.book_store_back.payment.application.dtos.ProcessSuccessfulPaymentCommand;

public interface ProcessSuccessfulPaymentUseCase {
    public void execute(ProcessSuccessfulPaymentCommand command);
}
