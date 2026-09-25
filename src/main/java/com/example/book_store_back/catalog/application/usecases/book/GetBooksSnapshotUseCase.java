package com.example.book_store_back.catalog.application.usecases.book;

import java.util.List;
import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.book.BookSnapshotResult;

public interface GetBooksSnapshotUseCase {
    public List<BookSnapshotResult> execute(List<UUID> bookIds);
}
