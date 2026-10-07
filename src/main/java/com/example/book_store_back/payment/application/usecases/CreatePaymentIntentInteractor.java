package com.example.book_store_back.payment.application.usecases;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.example.book_store_back.payment.application.dtos.CreatePaymentCommand;
import com.example.book_store_back.payment.application.dtos.PaymentIntentResult;
import com.example.book_store_back.payment.application.ports.FetchOrderInfo;
import com.example.book_store_back.payment.application.ports.PaymentGateway;
import com.example.book_store_back.payment.application.ports.PaymentRepository;
import com.example.book_store_back.payment.domain.GatewayTransactionId;
import com.example.book_store_back.payment.domain.Money;
import com.example.book_store_back.payment.domain.OrderId;
import com.example.book_store_back.payment.domain.Payment;


public class CreatePaymentIntentInteractor implements CreatePaymentIntentUseCase {

    private final FetchOrderInfo fetchOrderInfoPort;
    private final PaymentGateway paymentGatewayPort;
    private final PaymentRepository paymentRepositoryPort;

    public CreatePaymentIntentInteractor(
            FetchOrderInfo fetchOrderInfoPort,
            PaymentGateway paymentGatewayPort,
            PaymentRepository paymentRepositoryPort) {
        this.fetchOrderInfoPort = fetchOrderInfoPort;
        this.paymentGatewayPort = paymentGatewayPort;
        this.paymentRepositoryPort = paymentRepositoryPort;
    }

    @Transactional
    public String execute(CreatePaymentCommand command) {
        OrderId orderId = new OrderId(UUID.fromString(command.rawOrderId()));

        // 1. Seguridad: Obtener el precio real desde el backend, no del frontend
        Money amount = fetchOrderInfoPort.getOrderTotal(orderId);

        // 2. Hablar con la pasarela a través del puerto
        PaymentIntentResult intentResult = paymentGatewayPort.createPaymentIntent(orderId, amount);

        // 3. Crear la entidad de Dominio usando los Value Objects
        GatewayTransactionId gatewayId = new GatewayTransactionId(intentResult.gatewayTransactionId());

        Payment payment = Payment.create(orderId, amount, gatewayId);

        // 4. Guardar en base de datos (estado PENDING)
        paymentRepositoryPort.save(payment);

        // 5. Retornar solo el "client_secret" al controlador
        //  String usará Frontend para inyectar el iframe de la tarjeta
        return intentResult.clientSecret();
    }
}