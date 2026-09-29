package com.example.book_store_back.orders.infraestructure.persistence.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table
@Entity(name = "order_items")
@AllArgsConstructor  @NoArgsConstructor  @Getter @Setter
public class OrderItemEntity {

    @Id
    private UUID id;

    @Column(name = "book_id", nullable = false)
    private UUID bookId;

    @Column(name = "price_amount", nullable = false)
    private BigDecimal priceAmount;

    @Column(name = "price_currency", nullable = false)
    private String priceCurrency;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "format", nullable = false)
    private String format;

    @ManyToOne()
    @JoinColumn(name="order_id")
    private OrderEntity order;

}
