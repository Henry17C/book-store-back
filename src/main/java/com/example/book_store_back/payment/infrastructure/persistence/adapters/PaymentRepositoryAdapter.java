package com.example.book_store_back.payment.infrastructure.persistence.adapters;

import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.example.book_store_back.payment.application.ports.PaymentRepository;
import com.example.book_store_back.payment.domain.GatewayTransactionId;
import com.example.book_store_back.payment.domain.Payment;
import com.example.book_store_back.payment.infrastructure.persistence.entity.PaymentJpaEntity;
import com.example.book_store_back.payment.infrastructure.persistence.jpa.SpringDataPaymentRepository;
import com.example.book_store_back.payment.infrastructure.persistence.mappers.PaymentMapper;

@Component 
public class PaymentRepositoryAdapter implements PaymentRepository {

    private final SpringDataPaymentRepository jpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PaymentRepositoryAdapter(
            SpringDataPaymentRepository jpaRepository,
            ApplicationEventPublisher eventPublisher) {
        this.jpaRepository = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void save(Payment payment) {
        PaymentJpaEntity entity = PaymentMapper.toJpaEntity(payment);
        jpaRepository.save(entity);

        // Extraer y limpiar los eventos del agregado en memoria
        // Los publicar al bus de Spring para que el módulo de Órdenes los escuche
        payment.pullDomainEvents().forEach(eventPublisher::publishEvent);
    }

    @Override
    public Optional<Payment> findByGatewayTransactionId(GatewayTransactionId gatewayTransactionId) {

        PaymentJpaEntity entity = jpaRepository.findByGatewayTransactionId(gatewayTransactionId.toString())
                .orElseThrow(() -> {
                    return new IllegalArgumentException("Pago no encontrado.");
                });

        Payment domain = PaymentMapper.toDomain(entity);
        return Optional.of(domain);
    }

}
