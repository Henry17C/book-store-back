package com.example.book_store_back.payment.infrastructure.api;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.net.Webhook;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.example.book_store_back.payment.application.dtos.ProcessFailedPaymentCommand;
import com.example.book_store_back.payment.application.dtos.ProcessSuccessfulPaymentCommand;
import com.example.book_store_back.payment.application.usecases.ProcessFailedPaymentUseCase;
import com.example.book_store_back.payment.application.usecases.ProcessSuccessfulPaymentUseCase;

@RestController
@RequestMapping("/webhooks/payments")
public class StripeWebhookController {

    private final ProcessSuccessfulPaymentUseCase processSuccessfulPaymentUseCase;
    private final String webhookSecret;
    private final ProcessFailedPaymentUseCase processFailedPaymentUseCase;

    // Inyectar el Caso de Uso y el secreto del webhook
    public StripeWebhookController(
            ProcessSuccessfulPaymentUseCase processSuccessfulPaymentUseCase,
            ProcessFailedPaymentUseCase processFailedPaymentUseCase,
            @Value("${stripe.webhook.secret}") String webhookSecret) {
        this.processSuccessfulPaymentUseCase = processSuccessfulPaymentUseCase;
        this.webhookSecret = webhookSecret;
        this.processFailedPaymentUseCase = processFailedPaymentUseCase;
    }

    @PostMapping("/stripe")
    @Transactional // Iniciar la transacción
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload, // ¡Debe ser String crudo!
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;

        try {
            // 1. Validar la firma criptográfica. Si un hacker intenta enviar JSON falso,
            // esto lanzará SignatureVerificationException.
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

        } catch (SignatureVerificationException e) {
            System.err.println("⚠️ Alerta: Firma de webhook inválida.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Firma inválida");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error procesando payload");
        }

        // 2. Filtrar por el tipo de evento que nos interesa
        if ("payment_intent.succeeded".equals(event.getType())) {

            // 3. Extraer el PaymentIntent de adentro del evento de Stripe
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();

            if (dataObjectDeserializer.getObject().isPresent()) {
                PaymentIntent paymentIntent = (PaymentIntent) dataObjectDeserializer.getObject().get();

                // 4. Armar el comando con el ID (ej: pi_3Mxyz...) y ejecutar el Caso de Uso
                ProcessSuccessfulPaymentCommand command = new ProcessSuccessfulPaymentCommand(paymentIntent.getId());

                processSuccessfulPaymentUseCase.execute(command);
            }
        } else if ("payment_intent.payment_failed".equals(event.getType())) {
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();

            if (dataObjectDeserializer.getObject().isPresent()) {
                PaymentIntent paymentIntent = (PaymentIntent) dataObjectDeserializer.getObject().get();

                // Ejecutar el caso de uso de fallo
                ProcessFailedPaymentCommand command = new ProcessFailedPaymentCommand(paymentIntent.getId());

                processFailedPaymentUseCase.execute(command);
            }
        }

        // 5. Siempre retornar 200 OK rápido.
        // Si no devuelve 200, Stripe asumirá que el servidor falló y reintentará
        // enviar el mismo webhook durante varios días.
        return ResponseEntity.ok("Success");
    }
}