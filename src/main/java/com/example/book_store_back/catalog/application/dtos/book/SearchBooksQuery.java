package com.example.book_store_back.catalog.application.dtos.book;

public record SearchBooksQuery(
        String keyword, // Lo que el usuario teclea (ejm. "Harry Potter" o "978-3-16")
        int page, // Para la paginación (ejm. 0)
        int size, // Tamaño de la página (ejm. 24)
        Boolean onlyInStock // El superpoder que ya creamos: checkbox "Solo disponibles"
) {

}
