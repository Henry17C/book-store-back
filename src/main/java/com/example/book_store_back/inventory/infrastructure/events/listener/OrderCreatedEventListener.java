package com.example.book_store_back.inventory.infrastructure.events.listener;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.book_store_back.inventory.application.usescases.CreateReservationUseCase;
import com.example.book_store_back.shared.domain.events.OrderCreatedEvent;

@Component
public class OrderCreatedEventListener {
    private final CreateReservationUseCase createReservationUseCase;

    public OrderCreatedEventListener(CreateReservationUseCase createReservationUseCase){
    this.createReservationUseCase=createReservationUseCase;
    }
// 1. Si falla por concurrencia (@Version), reintenta automáticamente
@Retryable(
        retryFor = ObjectOptimisticLockingFailureException.class,
        maxAttempts = 3, 
        backoff = @Backoff(delay = 150)
    )
    // 2. REQUIRES_NEW abre una transacción en la BD exclusiva para el inventario
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    // 3. Solo escucha si la orden se guardó con éxito en su propia tabla
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreatedReservation(OrderCreatedEvent event){
        createReservationUseCase.execute(event);
    }
}
