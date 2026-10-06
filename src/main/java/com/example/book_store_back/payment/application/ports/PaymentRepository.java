package com.example.book_store_back.payment.application.ports;

import com.example.book_store_back.payment.domain.Payment;

public interface PaymentRepository {
    public void save(Payment payment);
    
}
