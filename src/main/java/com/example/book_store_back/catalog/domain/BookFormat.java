package com.example.book_store_back.catalog.domain;

import java.util.Objects;
//Object value
public record BookFormat(FormatType value) {

    public enum FormatType {
        PAPERBACK, // Libro de tapa blanda
        HARDCOVER, // Libro de tapa dura
        EBOOK, // Libro digital
    }


    public BookFormat {
        Objects.requireNonNull(value, "El formato del libro no puede ser nulo.");
    }

    // Permite crear el Value Object directamente usando un String (ejm. desde un
    // JSON)
    public static BookFormat fromString(String formatStr) {
        if (formatStr == null || formatStr.strip().isEmpty()) {
            throw new IllegalArgumentException("El texto del formato no puede estar vacío.");
        }
        try {
            // Convierte el texto a mayúsculas para evitar errores de escritura (ejm: "ebook"
            // -> EBOOK)
            FormatType type = FormatType.valueOf(formatStr.toUpperCase().trim());
            return new BookFormat(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Formato de libro no válido: '" + formatStr + "'. " +
                    "Los formatos aceptados son: PAPERBACK, HARDCOVER o EBOOK.");
        }
    }

    public boolean isDigital() {
        return this.value == FormatType.EBOOK;
    }

    public boolean isPhysical() {
        return this.value == FormatType.PAPERBACK || this.value == FormatType.HARDCOVER;
    }

}
