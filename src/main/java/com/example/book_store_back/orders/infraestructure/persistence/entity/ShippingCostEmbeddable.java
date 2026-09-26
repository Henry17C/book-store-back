package com.example.book_store_back.orders.infraestructure.persistence.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingCostEmbeddable {

    @Column(name = "shipping_cost_base_amount")
    private BigDecimal baseAmount;

    @Column(name = "shipping_cost_iva_amount")
    private BigDecimal ivaAmount;

    @Column(name = "shipping_cost_total_amount")
    private BigDecimal totalAmount;
}
