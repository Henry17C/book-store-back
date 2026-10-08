package com.example.book_store_back.orders.infraestructure.integration;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.book_store_back.catalog.application.api.CatalogInternalApi;
import com.example.book_store_back.orders.application.ports.CatalogGateway;
import com.example.book_store_back.orders.domain.ItemFormat;

@Component 
public class CatalogGatewayAdapter implements CatalogGateway {

    private final CatalogInternalApi catalogInternalApi;

    public CatalogGatewayAdapter(CatalogInternalApi catalogInternalApi) {
        this.catalogInternalApi = catalogInternalApi;
    }

    @Override
    public List<BookSnapshotData> getBooksDetails(List<UUID> bookIds) {

        List<BookSnapshotData> booksSnapshotData = catalogInternalApi.getBooksSnapshots(bookIds).stream().map(b -> {
            ItemFormat format = ItemFormat.valueOf(b.format());
            return new BookSnapshotData(b.id(), b.priceAmount(), b.priceCurrency(), format);
        }).toList();
        return booksSnapshotData;
    };

}
