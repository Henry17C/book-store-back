package com.example.book_store_back.orders.domain;

// VO
import java.util.Objects;
public record ShippingAddress(
    String province,
    String city,
    String sector,
    String place,          // Casa, Trabajo, Oficina, etc.
    String phone,
    String recipient,
    String mainStreet,
    String number,
    String crossStreet,
    String neighborhood,
    String reference,
    String zipCode,
    String country
) {
    private static final String ZIP_REGEX = "^\\d{6}$"; // Código postal Ecuador
    private static final String PHONE_REGEX = "^(\\+593)?[0-9]{9}$"; // Teléfono Ecuador


    public ShippingAddress {
        // Requeridos
        Objects.requireNonNull(province, "La provincia es obligatoria");
        Objects.requireNonNull(city, "La ciudad es obligatoria");
        Objects.requireNonNull(mainStreet, "La calle principal es obligatoria");
        Objects.requireNonNull(number, "El número es obligatorio");
        Objects.requireNonNull(zipCode, "El código postal es obligatorio");
        Objects.requireNonNull(country, "El país es obligatorio");

        // Validaciones básicas
        if (province.isBlank()) throw new IllegalArgumentException("La provincia no puede estar vacía");
        if (city.isBlank()) throw new IllegalArgumentException("La ciudad no puede estar vacía");
        if (mainStreet.isBlank()) throw new IllegalArgumentException("La calle principal no puede estar vacía");
        if (number.isBlank()) throw new IllegalArgumentException("El número no puede estar vacío");

        if (!zipCode.matches(ZIP_REGEX)) throw new IllegalArgumentException("El código postal debe tener 6 dígitos");
        if (!country.equalsIgnoreCase("Ecuador")) throw new IllegalArgumentException("El país debe ser Ecuador");

        if (phone != null && !phone.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("Número de teléfono inválido para Ecuador");
        }
    }
}