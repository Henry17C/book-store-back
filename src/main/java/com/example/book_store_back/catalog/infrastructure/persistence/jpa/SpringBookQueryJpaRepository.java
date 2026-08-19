package com.example.book_store_back.catalog.infrastructure.persistence.jpa;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.book_store_back.catalog.infrastructure.persistence.entities.BookEntity;
@Repository
public interface  SpringBookQueryJpaRepository extends JpaRepository<BookEntity, UUID> {
    
    // 1. Busqueda por Keyword (Título o ISBN)
    @Query("SELECT b FROM BookEntity b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR b.isbnValue = :keyword")
    Page<BookEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // 2. Nuevos lanzamientos (Filtra por stock si es necesario y ordena por fecha)
    @Query("SELECT b FROM BookEntity b WHERE (:onlyInStock = false OR b.hasStock = true) ORDER BY b.releaseDate DESC")
    Page<BookEntity> findNewReleases(@Param("onlyInStock") boolean onlyInStock, Pageable pageable);

    // 3. Recomendados (Filtra por isRecommended y stock)
    @Query("SELECT b FROM BookEntity b WHERE b.isRecommended = true AND (:onlyInStock = false OR b.hasStock = true)")
    Page<BookEntity> findRecommended(@Param("onlyInStock") boolean onlyInStock, Pageable pageable);

    // 4. Best Sellers (mejor calificación y más resenas)
    @Query("SELECT b FROM BookEntity b WHERE (:onlyInStock = false OR b.hasStock = true) ORDER BY b.averageRating DESC, b.totalReviews DESC")
    Page<BookEntity> findBestSellers(@Param("onlyInStock") boolean onlyInStock, Pageable pageable);
}
