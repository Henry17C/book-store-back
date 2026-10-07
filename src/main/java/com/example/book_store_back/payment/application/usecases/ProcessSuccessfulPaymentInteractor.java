package com.example.book_store_back.payment.application.usecases;

import com.example.book_store_back.payment.application.dtos.ProcessSuccessfulPaymentCommand;
import com.example.book_store_back.payment.application.ports.PaymentRepository;
import com.example.book_store_back.payment.domain.GatewayTransactionId;
import com.example.book_store_back.payment.domain.Payment;

public class ProcessSuccessfulPaymentInteractor implements ProcessSuccessfulPaymentUseCase{
    

    private final PaymentRepository paymentRepository;

    public ProcessSuccessfulPaymentInteractor(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    @Override
    public void execute(ProcessSuccessfulPaymentCommand command) {
        
        // 1. Convertir el texto que llegó en el webhook al Value Object
        GatewayTransactionId gatewayId = new GatewayTransactionId(command.rawGatewayTransactionId());

        // 2. Buscar el pago en la base de datos usando el ID de la pasarela
        Payment payment = paymentRepository.findByGatewayTransactionId(gatewayId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Alerta: Webhook recibido para una transacción desconocida: " + gatewayId.value()));

        // 3. Regla de negocio: Marcar como completado
        payment.markAsCompleted();

        // 4. Persistir cambios y publicar eventos
        // Al llamar a save(), la capa de infraestructura actualizará la DB
        // y automáticamente llamará a payment.pullDomainEvents() para publicarlos.
        paymentRepository.save(payment);
    }
}
