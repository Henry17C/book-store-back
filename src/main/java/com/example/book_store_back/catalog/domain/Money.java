package com.example.book_store_back.catalog.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

//Value object
public record Money(BigDecimal amount, String currency) {

    public Money {
        Objects.requireNonNull(amount, "El monto no puede ser nulo.");
        Objects.requireNonNull(currency, "La divisa no puede ser nula");

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }

        // Valida que el código de moneda sea un estándar ISO 4217 real (ejm: "USD",
        // "EUR", "MXN")
        try {
            Currency.getInstance(currency);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La divisa " + currency + "' no es un código ISO 4217 válido.");
        }
        // Redondeo
        amount = amount.setScale(2, RoundingMode.HALF_EVEN);

    }

    // Metodos de la definición
    public Money add(Money other) {
        Objects.requireNonNull(other, "No se puede sumar un objeto nulo.");
        checkSameCurrency(other);
        // Retorno de la nueva instancia
        return new Money(this.amount.add(other.amount()), this.currency);
    }

    public Money subtract(Money other) {
        Objects.requireNonNull(other, "No se puede restar un objeto nulo.");
        checkSameCurrency(other);
        // Retorno de la nueva instancia

        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    public Money convertTo(String targetCurrency) {
        Objects.requireNonNull(targetCurrency, "La divisa de destino no puede ser nula.");

        if (this.currency.equalsIgnoreCase(targetCurrency)) {
            return this;
        }

        // ADVERTENCIA DE ARQUITECTURA!!!!: El dominio NO debe conocer tasas de cambio
        // dinamicas (API externas).
        // Las tasas de cambio deben inyectarse o resolverse desde la capa de aplicación
        // o infraestructura.
        // Aqui simulamos una conversión conceptual básica o lanzamos una excepción si
        // requiere un servicio.
        throw new UnsupportedOperationException(
                "La conversión de moneda requiere consultar una tasa de cambio externa. Debe gestionarse a través de un servicio de dominio o aplicación.");
    }

    // --- METODO AUXILIAR ---

    private void checkSameCurrency(Money other) {
        if (!this.currency.equalsIgnoreCase(other.currency())) {
            throw new IllegalArgumentException(
                    "No se pueden realizar operaciones matemáticas en divisas diferentes: "
                            + this.currency + " y " + other.currency());
        }
    }

}
