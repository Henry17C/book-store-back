package com.example.book_store_back.orders.infraestructure.events.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.book_store_back.orders.application.dtos.CancelOrderCommand;
import com.example.book_store_back.orders.application.dtos.MarkOrderAsAwaitingPaymentCommand;
import com.example.book_store_back.orders.application.usecases.CancelOrderUseCase;
import com.example.book_store_back.orders.application.usecases.MarkOrderAsAwaitingPaymentUseCase;
import com.example.book_store_back.shared.domain.events.StockReservedEvent;
import com.example.book_store_back.shared.domain.events.StockReservedFailedEvent;

@Component
public class InventoryResponseEventListener {
    private final MarkOrderAsAwaitingPaymentUseCase markOrderAsAwaitingPaymentUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    public InventoryResponseEventListener(
            MarkOrderAsAwaitingPaymentUseCase markOrderAsAwaitingPaymentUseCase,
            CancelOrderUseCase cancelOrderUseCase) {
        this.markOrderAsAwaitingPaymentUseCase = markOrderAsAwaitingPaymentUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
    }

    @TransactionalEventListener
    public void onReserved(StockReservedEvent event) {
        MarkOrderAsAwaitingPaymentCommand command = new MarkOrderAsAwaitingPaymentCommand(event.orderId());

        markOrderAsAwaitingPaymentUseCase.execute(command);
    }

    @TransactionalEventListener
    public void onStockReservationFailed(StockReservedFailedEvent event) {

        CancelOrderCommand command = new CancelOrderCommand(event.orderId(), event.reason());

        cancelOrderUseCase.execute(command);
    }
}
