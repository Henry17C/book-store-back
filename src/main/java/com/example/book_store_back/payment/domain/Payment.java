package com.example.book_store_back.payment.domain;

import java.util.Objects;

import com.example.book_store_back.shared.domain.events.PaymentApprovedEvent;

// Aggregate
public class Payment extends AggregateRoot {
    private final PaymentId id;
    private final OrderId orderId;
    private final Money price;
    private PaymentStatus status;
    private final GatewayTransactionId gatewayTransactionId;

    private Payment(PaymentId id, OrderId orderId, Money price, PaymentStatus status,
            GatewayTransactionId gatewayTransactionId) {

        this.id = Objects.requireNonNull(id, "El ID del pago no puede ser nulo");
        this.orderId = Objects.requireNonNull(orderId, "El ID de la orden no puede ser nulo");
        this.price = Objects.requireNonNull(price, "El precio del pago no puede ser nulo");
        this.status = Objects.requireNonNull(status, "El estado del pago no puede ser nulo");
        this.gatewayTransactionId = Objects.requireNonNull(gatewayTransactionId,
                "El ID de la transacción no puede ser nulo");
    }

    public static Payment create(OrderId orderId, Money price, GatewayTransactionId gatewayTransactionId) {
        return new Payment(
                PaymentId.generateId(),
                orderId,
                price,
                PaymentStatus.PENDING,
                gatewayTransactionId);
    }

    public static Payment reconstitute(PaymentId id, OrderId orderId, Money price, PaymentStatus status,
            GatewayTransactionId gatewayTransactionId) {
        return new Payment(id, orderId, price, status, gatewayTransactionId);
    }

    public void markAsCompleted() {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException(
                    "Solo los pagos pendientes pueden ser completados. Estado actual: " + this.status);
        }
        this.status = PaymentStatus.COMPLETED;
        registerEvent(new PaymentApprovedEvent(this.orderId.value().toString(), this.id.value().toString()));
    }

    public void markAsFailed() {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("Solo los pagos pendientes pueden fallar. Estado actual: " + this.status);
        }
        this.status = PaymentStatus.FAILED;

        // registerEvent(new PaymentFailedEvent(this.orderId.value().toString()));
    }

    public void markAsRefunded() {
        if (this.status != PaymentStatus.COMPLETED) {
            throw new IllegalStateException(
                    "Solo los pagos completados pueden ser reembolsados. Estado actual: " + this.status);
        }
        this.status = PaymentStatus.REFUNDED;

        // registerEvent(new PaymentRefundedEvent(...));
    }

    // Getters

    public PaymentId getId() {
        return id;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public Money getPrice() {
        return price;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public GatewayTransactionId getGatewayTransactionId() {
        return gatewayTransactionId;
    }

}
