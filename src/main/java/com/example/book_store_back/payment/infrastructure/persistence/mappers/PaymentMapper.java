package com.example.book_store_back.payment.infrastructure.persistence.mappers;


import com.example.book_store_back.payment.domain.GatewayTransactionId;
import com.example.book_store_back.payment.domain.Money;
import com.example.book_store_back.payment.domain.OrderId;
import com.example.book_store_back.payment.domain.Payment;
import com.example.book_store_back.payment.domain.PaymentId;
import com.example.book_store_back.payment.infrastructure.persistence.entity.PaymentJpaEntity;

public class PaymentMapper {

    public static PaymentJpaEntity toJpaEntity(Payment domain) {
        PaymentJpaEntity entity = new PaymentJpaEntity();
        
        entity.setId(domain.getId().value()); 
        entity.setOrderId(domain.getOrderId().value());
        
        entity.setPriceAmount(domain.getPrice().amount());
        entity.setPriceCurrency(domain.getPrice().currency());
        
        entity.setStatus(domain.getStatus());
        entity.setGatewayTransactionId(domain.getGatewayTransactionId().value());
        
        return entity;
    }

    public static Payment toDomain(PaymentJpaEntity entity) {
        return Payment.reconstitute(
                new PaymentId(entity.getId()),
                new OrderId(entity.getOrderId()),
                new Money(entity.getPriceAmount(), entity.getPriceCurrency()),
                entity.getStatus(),
                new GatewayTransactionId(entity.getGatewayTransactionId())
        );
    }
}