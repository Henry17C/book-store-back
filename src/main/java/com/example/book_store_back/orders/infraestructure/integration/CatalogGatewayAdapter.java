package com.example.book_store_back.orders.infraestructure.integration;

import java.util.List;
import java.util.UUID;

import com.example.book_store_back.orders.application.ports.CatalogGateway;

public class CatalogGatewayAdapter implements CatalogGateway {

    @Override
    public List<BookSnapshotData> getBooksDetails(List<UUID> bookIds) {
       // TODO: Implementar cuando el caso de uso GetBooksSnapshotUseCase del catálogo esté listo
        throw new UnsupportedOperationException("La integración con el módulo de catálogo aún no está implementada.");
    };

}
