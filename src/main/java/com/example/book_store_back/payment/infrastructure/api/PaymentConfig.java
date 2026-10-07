package com.example.book_store_back.payment.infrastructure.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.book_store_back.payment.application.ports.FetchOrderInfo;
import com.example.book_store_back.payment.application.ports.PaymentGateway;
import com.example.book_store_back.payment.application.ports.PaymentRepository;
import com.example.book_store_back.payment.application.usecases.CreatePaymentIntentInteractor;
import com.example.book_store_back.payment.application.usecases.CreatePaymentIntentUseCase;
import com.example.book_store_back.payment.application.usecases.ProcessSuccessfulPaymentInteractor;
import com.example.book_store_back.payment.application.usecases.ProcessSuccessfulPaymentUseCase;

@Configuration
public class PaymentConfig {

    @Bean
    public CreatePaymentIntentUseCase createPaymentIntentUseCase(FetchOrderInfo fetchOrderInfoPort,
            PaymentGateway paymentGatewayPort,
            PaymentRepository paymentRepositoryPort) {

        return new CreatePaymentIntentInteractor(fetchOrderInfoPort, paymentGatewayPort, paymentRepositoryPort);

    }

    @Bean
    public ProcessSuccessfulPaymentUseCase processSuccessfulPaymentUseCase(
        PaymentRepository paymentRepository) {
        return new ProcessSuccessfulPaymentInteractor(paymentRepository);
    }

}
