package com.example.book_store_back.orders.infraestructure.events.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.book_store_back.orders.application.dtos.MarkOrderAsPaidCommand;
import com.example.book_store_back.orders.application.usecases.MarkOrderAsPaidUseCase;
import com.example.book_store_back.shared.domain.events.PaymentApprovedEvent;


@Component
public class PaymentResponseEventListener {
    private final MarkOrderAsPaidUseCase markOrderAsPaidUseCase;

    public PaymentResponseEventListener(MarkOrderAsPaidUseCase markOrderAsPaidUseCase) {
        this.markOrderAsPaidUseCase = markOrderAsPaidUseCase;
    }

    @TransactionalEventListener
    public void onPaymentApproved(PaymentApprovedEvent event) {
        MarkOrderAsPaidCommand command = new MarkOrderAsPaidCommand(event.orderId());
        markOrderAsPaidUseCase.execute(command);
    }
}
