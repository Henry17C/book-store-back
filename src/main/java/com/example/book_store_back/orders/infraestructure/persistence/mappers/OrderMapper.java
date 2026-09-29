package com.example.book_store_back.orders.infraestructure.persistence.mappers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.book_store_back.orders.domain.BookId;
import com.example.book_store_back.orders.domain.CustomerId;
import com.example.book_store_back.orders.domain.ItemFormat;
import com.example.book_store_back.orders.domain.Order;
import com.example.book_store_back.orders.domain.OrderId;
import com.example.book_store_back.orders.domain.OrderItem;
import com.example.book_store_back.orders.domain.PriceSnapshot;
import com.example.book_store_back.orders.domain.Quantity;
import com.example.book_store_back.orders.domain.ShippingAddress;
import com.example.book_store_back.orders.domain.ShippingCost;
import com.example.book_store_back.orders.infraestructure.persistence.entity.OrderEntity;
import com.example.book_store_back.orders.infraestructure.persistence.entity.OrderItemEntity;
import com.example.book_store_back.orders.infraestructure.persistence.entity.ShippingAddressEmbeddable;
import com.example.book_store_back.orders.infraestructure.persistence.entity.ShippingCostEmbeddable;

@Component
public class OrderMapper {

    public static OrderEntity toEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId().value());
        entity.setCustomerId(order.getCustomerId().value());
        entity.setCreatedAt(order.getCreatedAt());
        entity.setStatus(order.getStatus());

        // Mapeo condicional del VO de dirección
        if (order.getShippingAddress() != null) {
            ShippingAddressEmbeddable addressEntity = new ShippingAddressEmbeddable();
            addressEntity.setProvince(order.getShippingAddress().province());
            addressEntity.setCity(order.getShippingAddress().city());
            addressEntity.setSector(order.getShippingAddress().sector());
            addressEntity.setPlace(order.getShippingAddress().place());
            addressEntity.setPhone(order.getShippingAddress().phone());
            addressEntity.setRecipient(order.getShippingAddress().recipient());
            addressEntity.setMainStreet(order.getShippingAddress().mainStreet());
            addressEntity.setNumber(order.getShippingAddress().number());
            addressEntity.setCrossStreet(order.getShippingAddress().crossStreet());
            addressEntity.setNeighborhood(order.getShippingAddress().neighborhood());
            addressEntity.setReference(order.getShippingAddress().reference());
            addressEntity.setZipCode(order.getShippingAddress().zipCode());
            addressEntity.setCountry(order.getShippingAddress().country());

            entity.setShippingAddress(addressEntity);
        }

        // Mapeo del VO de costo de envío
        if (order.getShippingCost() != null) { // Costo nulo en digital
            ShippingCostEmbeddable costEntity = new ShippingCostEmbeddable();
            costEntity.setBaseAmount(order.getShippingCost().baseAmount());
            costEntity.setIvaAmount(order.getShippingCost().ivaAmount());
            costEntity.setTotalAmount(order.getShippingCost().totalAmount());
            entity.setShippingCost(costEntity);
        }

        // Mapeo de Items
        List<OrderItemEntity> itemEntities = order.getOrderItems().stream().map(domainItem -> {
            OrderItemEntity itemEntity = new OrderItemEntity();
            itemEntity.setBookId(UUID.fromString(domainItem.getBookId().value()));
            itemEntity.setFormat(domainItem.getItemFormat().toString());
            itemEntity.setQuantity(domainItem.getQuantity().value());
            itemEntity.setPriceAmount(domainItem.getUnitPrice().amount());
            itemEntity.setPriceCurrency(domainItem.getUnitPrice().currency());
            itemEntity.setOrder(entity); // Establecer la relación bidireccional JPA
            return itemEntity;
        }).collect(Collectors.toList());

        entity.setItems(itemEntities);
        return entity;
    }

    public static Order toDomain(OrderEntity entity) {

        // Reconstruir VOs si las columnas de la BD no están vacías
        ShippingAddress address = null;
        if (entity.getShippingAddress() != null && entity.getShippingAddress().getProvince() != null) {
            address = new ShippingAddress(
                    entity.getShippingAddress().getProvince(),
                    entity.getShippingAddress().getCity(),
                    entity.getShippingAddress().getSector(),
                    entity.getShippingAddress().getPlace(),
                    entity.getShippingAddress().getPhone(),
                    entity.getShippingAddress().getRecipient(),
                    entity.getShippingAddress().getMainStreet(),
                    entity.getShippingAddress().getNumber(),
                    entity.getShippingAddress().getCrossStreet(),
                    entity.getShippingAddress().getNeighborhood(),
                    entity.getShippingAddress().getReference(),
                    entity.getShippingAddress().getZipCode(),
                    entity.getShippingAddress().getCountry());
        }

        ShippingCost cost = new ShippingCost(
                entity.getShippingCost().getBaseAmount(),
                entity.getShippingCost().getIvaAmount(),
                entity.getShippingCost().getTotalAmount());

        List<OrderItem> items = entity.getItems().stream().map(itemEntity -> new OrderItem(
                new BookId(itemEntity.getBookId().toString()),
                ItemFormat.valueOf(itemEntity.getFormat()),
                new PriceSnapshot( itemEntity.getPriceAmount(), itemEntity.getPriceCurrency()),
                new Quantity(itemEntity.getQuantity()))).collect(Collectors.toList());

        // Reconstruir el Aggregate Root (Usando el constructor completo)
        return new Order(
                new OrderId(entity.getId()),
                new CustomerId(entity.getCustomerId()),
                address,
                cost,
                entity.getCreatedAt(),
                entity.getStatus(),
                items);
    }
}