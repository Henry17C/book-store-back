package com.example.book_store_back.payment.infrastructure.persistence.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book_store_back.payment.infrastructure.persistence.entity.PaymentJpaEntity;

public interface SpringDataPaymentRepository extends JpaRepository<PaymentJpaEntity, UUID>{
    
    public Optional<PaymentJpaEntity> findByGatewayTransactionId(String gatewayTransactionId);
}
