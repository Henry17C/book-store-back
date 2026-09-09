package com.example.book_store_back.identity.domain;

public record ProfilePictureUrl(String value) {

    public ProfilePictureUrl {
        if (value != null || !value.isBlank()) {

            if (!value.startsWith("http://") && !value.startsWith("https://")) {
                throw new IllegalArgumentException("La foto de perfil debe ser una URL válida (http o https)");
            }

            if (value.length() > 500) {
                throw new IllegalArgumentException("La URL de la foto de perfil es demasiado larga");

            }
        }
    }
}
