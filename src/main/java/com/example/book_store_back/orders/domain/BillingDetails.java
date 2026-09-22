package com.example.book_store_back.orders.domain;

import java.util.Objects;
import java.util.regex.Pattern;
// VO
public record BillingDetails(
    DocumentType documentType,
    String documentNumber,
    String businessName,
    String email,
    String mobileNumber,
    String phoneNumber,
    String address
) {
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final String MOBILE_REGEX = "^(\\+593|0)?9\\d{8}$"; // Celular Ecuador
    private static final String PHONE_REGEX = "^(\\+593|0)?[2-8]\\d{7}$"; // Fijo Ecuador

    public BillingDetails {
        // Requeridos principales
        Objects.requireNonNull(documentType, "El tipo de documento es obligatorio");
        Objects.requireNonNull(documentNumber, "El número de documento es obligatorio");
        Objects.requireNonNull(businessName, "La razón social es obligatoria");
        Objects.requireNonNull(email, "El email es obligatorio");
        Objects.requireNonNull(address, "La dirección es obligatoria");

        // Validaciones de vacío
        if (documentNumber.isBlank()) throw new IllegalArgumentException("El número de documento no puede estar vacío");
        if (businessName.isBlank()) throw new IllegalArgumentException("La razón social no puede estar vacía");
        if (address.isBlank()) throw new IllegalArgumentException("La dirección no puede estar vacía");

        // Validación de Email
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new IllegalArgumentException("El formato del correo electrónico es inválido");
        }

        // Validación de Documentos (Ecuador)
        if (documentType == DocumentType.CEDULA && !documentNumber.matches("^\\d{10}$")) {
            throw new IllegalArgumentException("La cédula debe tener exactamente 10 dígitos");
        }
        if (documentType == DocumentType.RUC && !documentNumber.matches("^\\d{13}$")) {
            throw new IllegalArgumentException("El RUC debe tener exactamente 13 dígitos");
        }

        // Validación de Teléfonos (Permitiendo nulos o vacíos por si el usuario solo ingresa uno)
        if (mobileNumber != null && !mobileNumber.isBlank() && !mobileNumber.matches(MOBILE_REGEX)) {
            throw new IllegalArgumentException("Número de celular inválido para Ecuador");
        }
        if (phoneNumber != null && !phoneNumber.isBlank() && !phoneNumber.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("Número de teléfono fijo inválido para Ecuador");
        }
    }
}