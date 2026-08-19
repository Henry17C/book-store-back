package com.example.book_store_back.catalog.infrastructure.persistence.adapters;

import org.springframework.stereotype.Component;

import com.example.book_store_back.catalog.application.ports.InventoryGateway;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DummyInventoryGatewayAdapter implements InventoryGateway {

    @Override
    public Boolean isAvariable(UUID bookId) {
        // Simulamos que el módulo de inventario responde que siempre hay stock
        return true;
    }

    @Override
    public Map<UUID, Boolean> checkStockInBatch(List<UUID> bookIds) {
        // Asignamos 'true' (hay stock) a todos los IDs que nos pregunten
        return bookIds.stream()
                .collect(Collectors.toMap(id -> id, id -> true));
    }
}