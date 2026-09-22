package com.example.book_store_back.orders.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

//Aggregate
public class Order {
    private final OrderId id;
    private final CustomerId customerId;
    private final ShippingAddress shippingAddress;
    private final LocalDateTime createdAt;
    private OrderStatus status;
    private final List<OrderItem> items;
    private ShippingCost shippingCost;

    public Order(OrderId id, CustomerId customerId, ShippingAddress shippingAddress, ShippingCost shippingCost,
            LocalDateTime createdAt,
            OrderStatus status, List<OrderItem> items) {
        this.id = Objects.requireNonNull(id, "El id de la order no puder ser nulo.");
        this.customerId = Objects.requireNonNull(customerId, "El id del cliente no puede ser nulo.");
        this.createdAt = Objects.requireNonNull(createdAt, "La fecha de creación no puede ser nula.");
        this.shippingCost = Objects.requireNonNull(shippingCost, "El costo de envío no puede ser nulo.");
        this.status = Objects.requireNonNull(status, "El estado de la orden no puede ser nulo.");
        this.items = new ArrayList<>(Objects.requireNonNull(items, "La lista de items no puede ser nula."));

        // Regla de Negocio: Validar si la orden necesita dirección basada en los items
        boolean requiresShipping = this.items.stream()
                .anyMatch(item -> item.getItemFormat().requiresShipping());

        if (requiresShipping && shippingAddress == null) {
            throw new IllegalArgumentException(
                    "La dirección de envío es obligatoria porque el pedido contiene libros físicos.");
        }

        // Si no requiere envío, shippingAddress puede ser null
        this.shippingAddress = shippingAddress;
    }

    public void setShippingCost(ShippingCost shippingCost) {
        if (this.status != OrderStatus.CREATED) {
            throw new IllegalStateException("Solo se puede modificar el envío en ordenes CREADAS");
        }
        this.shippingCost = Objects.requireNonNull(shippingCost, "El costo de envío no puede ser nulo");
    }

    // Factory
    public static Order create(CustomerId customerId, ShippingAddress shippingAddress, ShippingCost shippingCost,
            List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("No se puede crear una orden sin items.");
        }
        return new Order(OrderId.generate(), customerId, shippingAddress, shippingCost, LocalDateTime.now(),
                OrderStatus.CREATED,
                items);
    }

    public void addItem(BookId bookId, ItemFormat format, PriceSnapshot unitPrice, Quantity quantity) {
        if (this.status != OrderStatus.CREATED) {
            throw new IllegalStateException("No se pueden añadir artículos a un pedido que ya está " + this.status);
        }

        // Proteger la regla de negocio al añadir nuevos elementos dinámicamente
        if (format.requiresShipping() && this.shippingAddress == null) {
            throw new IllegalStateException("No se puede agregar un libro físico a un pedido que no tiene dirección de envío configurada.");
        }

        this.items.add(new OrderItem(bookId, format, unitPrice, quantity));

    }

    public void removeItem(BookId bookId) {
        if (this.status != OrderStatus.CREATED) {
            throw new IllegalStateException("Cannot remove items from an order that is already " + this.status);
        }
        boolean removed = this.items.removeIf(item -> item.getBookId().equals(bookId));

        if (!removed) {
            throw new IllegalArgumentException("El libro no existe en el pedido.");
        }
    }

    public void markAsPaid() {
        if (this.status != OrderStatus.CREATED) {
            throw new IllegalStateException("Solo ordenes CREADAS pueden ser PAGADAS");
        }
        if (this.items.isEmpty()) {
            throw new IllegalStateException("No se puede pagar una orden vacía.");
        }
        this.status = OrderStatus.PAID;
    }

    public void markAsShipped() {
        if (this.status != OrderStatus.PAID) {
            throw new IllegalStateException("La orden debe ser PAGADA antes de ser ENVIADA.");
        }
        this.status = OrderStatus.SHIPPED;
    }

    public void cancel() {
        if (this.status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("No se puede CANCELAR una orden que ya ha sido ENVIADA.");
        }
        this.status = OrderStatus.CANCELLED;
    }

    // --- CÁLCULOS FINANCIEROS ---

    // 1. Tarifa 0% (Suma de los libros)
    public BigDecimal calculateTarifa0() {
        return items.stream()
                .map(OrderItem::calculateSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 2. Tarifa 15% (El valor base del envío antes de IVA)
    public BigDecimal calculateTarifa15() {
        return shippingCost != null ? shippingCost.baseAmount() : BigDecimal.ZERO;
    }

    // 3. Subtotal sin Impuestos (Libros + Base del envío)
    public BigDecimal calculateSubtotalSinImpuestos() {
        return calculateTarifa0().add(calculateTarifa15());
    }

    // 4. IVA 15% (El impuesto generado por el envío)
    public BigDecimal calculateIva15() {
        return shippingCost != null ? shippingCost.ivaAmount() : BigDecimal.ZERO;
    }

    // 5. Total (Subtotal + IVA)
    public BigDecimal calculateTotal() {
        return calculateSubtotalSinImpuestos().add(calculateIva15());
    }

    // Getters

    public OrderId getId() {
        return id;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(this.items);
    }

}
