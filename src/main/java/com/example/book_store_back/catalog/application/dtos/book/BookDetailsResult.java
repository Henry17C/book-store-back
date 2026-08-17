package com.example.book_store_back.catalog.application.dtos.book;

import java.util.List;
import java.util.UUID;
public record BookDetailsResult(

        UUID id,
        String title,
        String isnb,
        String format,
        List<String> authorNames,
        Double averageRating,
        ///List<ReviewResponse> reviews, futura ampliacion de la funcionalidad
        String bookDescription,
        String bookCoverUrl,
        MoneyResul price, //amount and currency
        Boolean inStock
) {

}
