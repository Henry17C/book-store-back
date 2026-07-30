package com.example.book_store_back.catalog.domain;

import java.util.Objects;
import java.util.UUID;

//Aggregate
public class Author {
    private UUID id;
    private String name;
    private String biography;

    public Author(UUID id, String name, String biography) {
        this.id = Objects.requireNonNull(id, "El id del autor no puede ser nulo.");
        this.biography = biography;
        this.name = name;
    }

    public static Author register(UUID id, String name, String biography) {
        validateBiography(biography);
        validateName(name);
        return new Author(id, name, biography);
    }

    public void  updateAuthor(String name, String biography){
        this.name=Objects.requireNonNull(name, "El nombre no pueder ser nulo.");
        this.biography=Objects.requireNonNull(biography, "La biografía no pueder ser nulo.");
    }

    // Validación de la logica de negocio

    private static void validateName(String name) {
        if (name == null || name.strip().isEmpty()) {
            throw new IllegalArgumentException("Ingrese el nombre del Autor");
        }
    }

    private static void validateBiography(String biography) {
        if (biography == null || biography.strip().isEmpty()) {
            throw new IllegalArgumentException("Ingrese la biografía del Autor");
        }

    }

    // Getters
    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getBiography() {
        return this.biography;
    }
}
