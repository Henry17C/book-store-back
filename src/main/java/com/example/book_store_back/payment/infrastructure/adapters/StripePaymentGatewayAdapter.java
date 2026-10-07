package com.example.book_store_back.payment.infrastructure.adapters;

import com.example.book_store_back.payment.application.dtos.PaymentIntentResult;
import com.example.book_store_back.payment.application.ports.PaymentGateway;
import com.example.book_store_back.payment.domain.Money;
import com.example.book_store_back.payment.domain.OrderId;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StripePaymentGatewayAdapter implements PaymentGateway {

    // Spring inyecta la clave colocado en application.properties
    public StripePaymentGatewayAdapter(@Value("${stripe.secret.key}") String secretKey) {
        Stripe.apiKey = secretKey;
    }

    @Override
    public PaymentIntentResult createPaymentIntent(OrderId orderId, Money amount) {
        try {
            // Stripe exige que el dinero se envíe en la unidad menor (centavos)
            // Si Money dice $25.50, lo multiplica por 100 para enviar 2550
            long amountInCents = amount.amount().multiply(new BigDecimal("100")).longValue();

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency(amount.currency().toLowerCase()) // ej: "usd"
                    .putMetadata("orderId", orderId.value().toString()) // Clave para que vuelva en el Webhook
                    .build();

            // Petición síncrona a la API de Stripe
            PaymentIntent intent = PaymentIntent.create(params);

            // Retornar el ID para guardarlo en BD y el Secret para el Frontend
            return new PaymentIntentResult(intent.getId(), intent.getClientSecret());
            
        } catch (Exception e) {
            throw new RuntimeException("Error al generar la intención de pago con Stripe", e);
        }
    }
}