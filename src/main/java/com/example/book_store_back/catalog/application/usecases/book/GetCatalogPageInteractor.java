package com.example.book_store_back.catalog.application.usecases.book;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResult;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;
import com.example.book_store_back.catalog.application.ports.BookQueryGateway;
import com.example.book_store_back.catalog.application.ports.InventoryGateway;

public class GetCatalogPageInteractor implements GetCatalogPageUseCase {

    private final BookQueryGateway bookQueryGateway;

    private final InventoryGateway inventoryGateway;

    public GetCatalogPageInteractor(BookQueryGateway bookQueryGateway, InventoryGateway inventoryGateway) {
        this.bookQueryGateway = bookQueryGateway;
        this.inventoryGateway = inventoryGateway;
    }

    @Override
    public PageResult<CatalogBookResult> execute(int page, int size) {
        // 1. Validaciones de seguridad para paginación
        if (page < 0)
            page = 0;
        if (size <= 0 || size > 50)
            size = 20;
        // 2. Pedimos los libros a la BD (trae todo MENOS el stock, que vendrá en null o
        // false) (SERVICIO EXTERNO)
        PageResult<CatalogBookResult> resultWithoutStock = bookQueryGateway.getCatalogPage(page, size);

        // Si la página está vacía, no hacemos nada más
        if (resultWithoutStock.content().isEmpty()) {
            return resultWithoutStock;
        }
        // 3. Extraemos solo los IDs de los libros de esta página (ej. 20 IDs)
        List<UUID> ids = resultWithoutStock.content().stream().map(CatalogBookResult::id).toList();

        // 4. (SERVICIO EXTERNO)

        Map<UUID, Boolean> stockInfo = inventoryGateway.checkStockInBatch(ids);

        // 5. Construimos la lista final combinando la info del catálogo con el stock
        List<CatalogBookResult> finalContent = resultWithoutStock.content().stream().map(book -> {
            return new CatalogBookResult(book.id(), book.title(), book.coverUrl(), book.authorNames(), book.price(),
                    book.averageRating(),
                    // Obtenemos el stock del mapa, si no está, asumimos false
                    stockInfo.getOrDefault(book.id(), false), book.isnb());
        }).toList();

        // 6. Retornamos la página completa y lista para el frontend
        return new PageResult<>(finalContent, resultWithoutStock.currentPage(),
                resultWithoutStock.totalPages(), resultWithoutStock.totalElements());
    }

}
