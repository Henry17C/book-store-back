package com.example.book_store_back.orders.application.dtos;

import java.math.BigDecimal;
import java.util.List;

public record OrderDetailsResult(
        String orderId,
        String status,

        BigDecimal subtotalLibros, // order.calculateTarifa0()
        BigDecimal costoEnvioBase, // order.calculateTarifa15()
        BigDecimal iva, // order.calculateIva15()
        BigDecimal totalAmount, // order.calculateTotal()
        String cancellationReason,

        // Lista de ítems para mostrar al usuario qué está pagando
        List<OrderItemResult> items) {

}
