package com.example.book_store_back.orders.domain;

import java.math.BigDecimal;
import java.util.Objects;

public record ShippingCost(
    BigDecimal baseAmount,  // Ejm: 6.00
    BigDecimal ivaAmount,   // Ejm: 0.90
    BigDecimal totalAmount  // Ejm: 6.90
) {
    public ShippingCost {
        Objects.requireNonNull(baseAmount, "El monto base no puede ser nulo");
        Objects.requireNonNull(ivaAmount, "El monto del IVA no puede ser nulo");
        Objects.requireNonNull(totalAmount, "El monto total no puede ser nulo");
        
        // Regla de negocio: El total debe ser la suma de la base + IVA
        if (baseAmount.add(ivaAmount).compareTo(totalAmount) != 0) {
            throw new IllegalArgumentException("El total del envío debe ser la suma de la base y el IVA");
        }
    }
}