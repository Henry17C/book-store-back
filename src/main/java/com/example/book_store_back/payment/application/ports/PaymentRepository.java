package com.example.book_store_back.payment.application.ports;

import java.util.Optional;

import com.example.book_store_back.payment.domain.GatewayTransactionId;
import com.example.book_store_back.payment.domain.Payment;

public interface PaymentRepository {
    public void save(Payment payment);
    Optional<Payment> findByGatewayTransactionId(GatewayTransactionId gatewayTransactionId);
    
}
