package com.example.book_store_back.catalog.infrastructure.api.exception;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.book_store_back.catalog.domain.events.exception.AuthorNotFoundException;
import com.example.book_store_back.catalog.domain.events.exception.BookNotFoundException;
import com.example.book_store_back.catalog.domain.events.exception.ReviewNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 1. Manejo de Errores de Validación (@Valid, @NotNull, @NotBlank, etc.)
     * Se dispara cuando un DTO de entrada (Request) no cumple las reglas.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "La petición contiene errores de validación.");
        problemDetail.setTitle("Bad Request");
        problemDetail.setType(URI.create("https://api.mi-ecommerce.com/errors/bad-request"));
        problemDetail.setProperty("timestamp", Instant.now());

        // Extraer campos que  fallaron y sus mensajes (ejm. "price.amount": "El monto debe ser mayor a cero")
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        
        // Lista de errores detallados a la respuesta
        problemDetail.setProperty("invalid_params", errors);

        return problemDetail;
    }

    /**
     * 2. Manejo de Excepciones de Dominio (Ejm. No se encontró el libro)
     * Se dispara cuando la capa de Aplicación o Dominio lanza una excepción de negocio.
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ProblemDetail handleBookNotFoundException(BookNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create("https://api.mi-ecommerce.com/errors/not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        
        return problemDetail;
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ProblemDetail handleAuthorNotFoundException(AuthorNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create("https://api.mi-ecommerce.com/errors/not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        
        return problemDetail;
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ProblemDetail handleReviewNotFoundException(ReviewNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create("https://api.mi-ecommerce.com/errors/not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        
        return problemDetail;
    }

    /**
     * 3. Manejo de Errores Genéricos (Fallback)
     * Atrapa cualquier error no controlado (ejm. caída de base de datos, NullPointerException)
     * para no exponer el StackTrace de Java al usuario.
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex) {
        // Mejora futura: enviar 'ex.getMessage()' a un sistema de logs como Kibana o Sentry.
        ex.printStackTrace();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error interno en el servidor. Por favor, contacte a soporte.");
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("https://api.mi-ecommerce.com/errors/internal-error"));
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }
}