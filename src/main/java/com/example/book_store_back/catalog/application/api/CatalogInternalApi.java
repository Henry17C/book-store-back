package com.example.book_store_back.catalog.application.api;

import java.util.List;
import java.util.UUID;

public interface CatalogInternalApi {
    List<BookSnapshotDTO> getBooksSnapshots(List<UUID> bookIds);
}