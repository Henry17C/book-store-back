package com.example.book_store_back.payment.application.usecases;

import com.example.book_store_back.payment.application.dtos.CreatePaymentCommand;

public interface  CreatePaymentIntentUseCase {
        public String execute(CreatePaymentCommand command);

}
