package com.example.book_store_back.orders.infraestructure.integration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;

import org.springframework.stereotype.Component;

import com.example.book_store_back.orders.application.ports.ShippingPricingService;
import com.example.book_store_back.orders.domain.ShippingAddress;
import com.example.book_store_back.orders.domain.ShippingCost;

@Component
public class LocalShippingPricingAdapter implements ShippingPricingService {

    private static final BigDecimal IVA_RATE = new BigDecimal("0.15");

    // El precio por default provincias lejanas
    private static final BigDecimal DEFAULT_BASE_PRICE = new BigDecimal("6.00");

    @Override
    public ShippingCost calculateFor(ShippingAddress address) {
        // Solo evaluar la provincia
        BigDecimal baseAmount = determineBasePrice(address.province());

        // Calculo IVA
        BigDecimal ivaAmount = baseAmount.multiply(IVA_RATE).setScale(2, RoundingMode.HALF_UP);

        // Sumamos total
        BigDecimal totalAmount = baseAmount.add(ivaAmount).setScale(2, RoundingMode.HALF_UP);

        return new ShippingCost(baseAmount, ivaAmount, totalAmount);
    }

    private BigDecimal determineBasePrice(String province) {
        if (province == null || province.isBlank()) {
            return DEFAULT_BASE_PRICE;
        }

        // Limpieza de texto
        String normalizedProvince = Normalizer.normalize(province, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .trim();

        // Evaluar 24 provincias de Ecuador
        return switch (normalizedProvince) {
            // Sierra Centro-Norte
            case "pichincha", "imbabura", "cotopaxi", "tungurahua" -> new BigDecimal("4.17");

            // Distancias intermedias
            case "santo domingo de los tsachilas", "carchi", "chimborazo", "bolivar", "napo" -> new BigDecimal("5.00");

            // Insular
            case "galapagos" -> new BigDecimal("10.00");

            // Costa, Amazonía profunda y Sierra Sur
            default -> DEFAULT_BASE_PRICE;
        };
    }
}