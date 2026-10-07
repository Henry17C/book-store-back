package com.example.book_store_back.payment.application.usecases;

import com.example.book_store_back.payment.application.dtos.ProcessFailedPaymentCommand;
import com.example.book_store_back.payment.application.ports.PaymentRepository;
import com.example.book_store_back.payment.domain.GatewayTransactionId;
import com.example.book_store_back.payment.domain.Payment;

public class ProcessFailedPaymentInteractor implements ProcessFailedPaymentUseCase{
    private final PaymentRepository paymentRepository;

    public ProcessFailedPaymentInteractor(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    
    public void execute(ProcessFailedPaymentCommand command) {
        
        GatewayTransactionId gatewayId = new GatewayTransactionId(command.rawGatewayTransactionId());

        Payment payment = paymentRepository.findByGatewayTransactionId(gatewayId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Alerta: Webhook recibido para una transacción fallida desconocida: " + gatewayId.value()));

        // Regla de negocio: Marcar como fallido. 
        // Más adelante se decide que los pagos fallidos emitan un evento para notificar al usuario,
        // agregar dentro de este método markAsFailed() en el Agregado.
        payment.markAsFailed();

        // Persistir cambios
        paymentRepository.save(payment);
    }
}
