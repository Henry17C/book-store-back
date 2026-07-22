package com.example.book_store_back.catalog.domain;

//Value Object
public record Isbn(String value) {
    public Isbn {
        if (!isValid(value)) {
            throw new IllegalArgumentException("El formato del ISBN no es válido ");
        }
    }

    public static Boolean isValid(String isbn) {
        if (isbn == null) {
            return false;
        }
        // Eliminar guiones y espacios para normalizar la cadena
        String cleanIsbn = isbn.replaceAll("[\\s-]", "");

        // Expresiones regulares básicas para validar longitud y caracteres permitidos
        // ISBN-10: 9 dígitos + 1 dígito o una 'X'/'x' al final
        boolean matchesIsbn10 = cleanIsbn.matches("^[0-9]{9}[0-9Xx]$");
        // ISBN-13: 13 dígitos numéricos
        boolean matchesIsbn13 = cleanIsbn.matches("^[0-9]{13}$");

        if (!matchesIsbn10 && !matchesIsbn13) {
            return false;
        }
        return true;
    }
}
