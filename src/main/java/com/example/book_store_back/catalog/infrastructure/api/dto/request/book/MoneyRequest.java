package com.example.book_store_back.catalog.infrastructure.api.dto.request.book;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MoneyRequest(
        @NotNull(message = "El monto es obligatorio.")
        @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a cero.")
        BigDecimal amount,

        @NotBlank(message = "La moneda es obligatoria.")
        @Size(min = 3, max = 3, message = "El código de moneda debe tener 3 caracteres (ej. USD).")
        String currency
) {
}