package com.example.book_store_back.catalog.application.ports;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface InventoryGateway {
    // Para cuando se consulta un unico libro
    Boolean isAvariable(UUID bookId);

    // Para cuando se consulta una página entera de libros
    Map<UUID, Boolean> checkStockInBatch(List<UUID> bookIds);
}
