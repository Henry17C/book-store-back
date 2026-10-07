package com.example.book_store_back.payment.application.usecases;

import com.example.book_store_back.payment.application.dtos.ProcessFailedPaymentCommand;

public interface ProcessFailedPaymentUseCase {
    public void execute(ProcessFailedPaymentCommand command);
}
