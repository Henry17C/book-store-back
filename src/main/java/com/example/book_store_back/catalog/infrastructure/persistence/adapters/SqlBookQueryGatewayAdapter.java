package com.example.book_store_back.catalog.infrastructure.persistence.adapters;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResult;
import com.example.book_store_back.catalog.application.dtos.book.MoneyResul;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;
import com.example.book_store_back.catalog.application.ports.BookQueryGateway;
import com.example.book_store_back.catalog.infrastructure.persistence.entities.AuthorEntity;
import com.example.book_store_back.catalog.infrastructure.persistence.entities.BookEntity;
import com.example.book_store_back.catalog.infrastructure.persistence.jpa.SpringBookQueryJpaRepository;
import com.example.book_store_back.catalog.infrastructure.persistence.jpa.SpringDataAuthorRepository;
@Component
public class SqlBookQueryGatewayAdapter implements BookQueryGateway {

        private final SpringBookQueryJpaRepository queryRepository;
        private final SpringDataAuthorRepository authorRepository;

        public SqlBookQueryGatewayAdapter(SpringBookQueryJpaRepository queryRepository,
                        SpringDataAuthorRepository authorRepository) {
                this.queryRepository = queryRepository;
                this.authorRepository = authorRepository;
        }

        @Override
        public PageResult<CatalogBookResult> getCatalogPage(int page, int size) {
                Page<BookEntity> springPage = queryRepository.findAll(PageRequest.of(page, size));
                return mapToPageResult(springPage);
        }

        @Override
        public PageResult<CatalogBookResult> searchByKeyword(String sanitizedKeyword, int page, int size) {
                Page<BookEntity> springPage = queryRepository.searchByKeyword(sanitizedKeyword,
                                PageRequest.of(page, size));
                return mapToPageResult(springPage);
        }

        @Override
        public PageResult<CatalogBookResult> findNewReleases(int page, int size, Boolean onlyInStock) {
                Page<BookEntity> springPage = queryRepository.findNewReleases(onlyInStock != null && onlyInStock,
                                PageRequest.of(page, size));
                return mapToPageResult(springPage);
        }

        @Override
        public PageResult<CatalogBookResult> findRecommended(int page, int size, Boolean onlyInStock) {
                Page<BookEntity> springPage = queryRepository.findRecommended(onlyInStock != null && onlyInStock,
                                PageRequest.of(page, size));
                return mapToPageResult(springPage);
        }

        @Override
        public PageResult<CatalogBookResult> findBestSellers(int page, int size, Boolean onlyInStock) {
                Page<BookEntity> springPage = queryRepository.findBestSellers(onlyInStock != null && onlyInStock,
                                PageRequest.of(page, size));
                return mapToPageResult(springPage);
        }

        // ==========================================================
        // METODOS DE MAPEO (Traducción de Infra a Aplicación)
        // ==========================================================

        private PageResult<CatalogBookResult> mapToPageResult(Page<BookEntity> springPage) {

                // 1: Extraer todos los IDs únicos de autores de los libros de ESTA página
                Set<UUID> allAuthorIdsInPage = springPage.getContent().stream()
                                .flatMap(book -> book.getAuthorIds().stream())
                                .collect(Collectors.toSet());

                // 2: Buscar todos esos autores en una sola consulta a la BD y crear un
                // "Diccionario" (Map)
                Map<UUID, String> authorNamesMap = authorRepository.findAllById(allAuthorIdsInPage).stream()
                                .collect(Collectors.toMap(AuthorEntity::getId, AuthorEntity::getName));

                // 3: Mapear los libros e inyectarles los nombres usando el diccionario

                List<CatalogBookResult> content = springPage.getContent().stream()
                                .map(entity -> {
                                        // Traducir la lista de UUIDs a lista de Strings (Nombres)
                                        List<String> authorNames = entity.getAuthorIds().stream()
                                                        .map(id -> authorNamesMap.getOrDefault(id, "Autor Desconocido"))
                                                        .toList();

                                        return toCatalogBookResult(entity, authorNames);
                                })
                                .toList();

                return new PageResult<>(
                                content,
                                springPage.getNumber(),
                                springPage.getTotalPages(),
                                springPage.getTotalElements());
        }

        private CatalogBookResult toCatalogBookResult(BookEntity entity, List<String> authorNames) {
                return new CatalogBookResult(entity.getId(),
                                entity.getTitle(),
                                entity.getCoverUrl(),
                                authorNames,
                                new MoneyResul(entity.getPriceAmount(),
                                                entity.getPriceCurrency()),
                                entity.getAverageRating(),
                                entity.getHasStock());

        }
}
