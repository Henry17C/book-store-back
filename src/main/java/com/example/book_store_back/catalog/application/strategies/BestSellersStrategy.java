package com.example.book_store_back.catalog.application.strategies;

import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResult;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;
import com.example.book_store_back.catalog.application.ports.BookQueryGateway;

public class BestSellersStrategy implements CategoryStrategy {

    private final BookQueryGateway gateway;

    public BestSellersStrategy(BookQueryGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public boolean supports(CategoryCode categoryCode) {
        return CategoryCode.BEST_SELLERS.equals(categoryCode);
    }

    @Override
    public PageResult<CatalogBookResult> fetch(int page, int size, Boolean onlyInStock) {
        return gateway.findBestSellers(page, size, onlyInStock);
    }

}
