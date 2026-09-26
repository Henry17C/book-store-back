package com.example.book_store_back.orders.application.usecases;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.example.book_store_back.orders.application.dtos.CreateOrderCommand;
import com.example.book_store_back.orders.application.ports.CatalogGateway;
import com.example.book_store_back.orders.application.ports.CatalogGateway.BookSnapshotData;
import com.example.book_store_back.orders.application.ports.DomainEventPublisher;
import com.example.book_store_back.orders.application.ports.OrderRepository;
import com.example.book_store_back.orders.application.ports.ShippingPricingService;
import com.example.book_store_back.orders.domain.BookId;
import com.example.book_store_back.orders.domain.CustomerId;
import com.example.book_store_back.orders.domain.Order;
import com.example.book_store_back.orders.domain.OrderItem;
import com.example.book_store_back.orders.domain.PriceSnapshot;
import com.example.book_store_back.orders.domain.Quantity;
import com.example.book_store_back.orders.domain.ShippingAddress;
import com.example.book_store_back.orders.domain.ShippingCost;
import com.example.book_store_back.orders.domain.events.OrderCreatedEvent;
import com.example.book_store_back.orders.domain.events.OrderCreatedEvent.OrderItemEvent;

public class CreateOrderInteractor implements CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final CatalogGateway catalogGateway;
    private final ShippingPricingService shippingPricingService;
    private final DomainEventPublisher domainEventPublisher;

    public CreateOrderInteractor(
            OrderRepository orderRepository,
            CatalogGateway catalogGateway,
            ShippingPricingService shippingPricingService,
            DomainEventPublisher domainEventPublisher) {
        this.orderRepository = orderRepository;
        this.catalogGateway = catalogGateway;
        this.shippingPricingService = shippingPricingService;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    @Transactional
    public UUID execute(CreateOrderCommand command) {
        // 0. Instanciar VO simple
        CustomerId customerId = new CustomerId(command.customerId());

        // 1. Extraer todos los IDs de los libros que vienen en el comando
        List<UUID> requestedBookIds = command.items().stream()
                .map(item -> UUID.fromString(item.bookId()))
                .toList();

        // 2. Hacer UNA SOLA consulta al catálogo externo/base de datos
        List<BookSnapshotData> catalogBooks = catalogGateway.getBooksDetails(requestedBookIds);

        // 3. Convertir la lista en un Map para búsquedas rápidas (O(1)) por ID
        Map<UUID, BookSnapshotData> catalogMap = catalogBooks.stream()
                .collect(Collectors.toMap(BookSnapshotData::bookId, book -> book));

        // 4. Ensamblar las Entidades del Dominio
        List<OrderItem> validItems = new ArrayList<>();
        for (var itemCmd : command.items()) {
            UUID currentBookId = UUID.fromString(itemCmd.bookId());

            // Buscar en el Map local (en memoria RAM), no en la base de datos
            BookSnapshotData realBook = catalogMap.get(currentBookId);

            if (realBook == null) {
                throw new IllegalArgumentException(
                        "El libro " + currentBookId + " no existe o no está disponible en el catálogo");
            }

            validItems.add(new OrderItem(
                    new BookId(realBook.bookId().toString()),
                    realBook.format(),
                    new PriceSnapshot(realBook.price(), realBook.currency()),
                    new Quantity(itemCmd.quantity())));
        }

        // 5. Evaluar si necesita dirección (Regla de negocio sobre la lista de
        // entidades)
        boolean requiresShipping = validItems.stream()
                .anyMatch(item -> item.getItemFormat().requiresShipping());

        ShippingAddress address = null;
        ShippingCost cost;

        // 6. Instanciar VO complejo y calcular envío
        if (requiresShipping) {
            if (command.shippingAddress() == null) {
                throw new IllegalArgumentException(
                        "La dirección de envío es obligatoria en el comando para libros físicos.");
            }

            var addrCmd = command.shippingAddress();

            // El VO aplica todas sus reglas (Regex, nulos, vacíos)
            address = new ShippingAddress(
                    addrCmd.province(), addrCmd.city(), addrCmd.sector(), addrCmd.place(),
                    addrCmd.phone(), addrCmd.recipient(), addrCmd.mainStreet(), addrCmd.number(),
                    addrCmd.crossStreet(), addrCmd.neighborhood(), addrCmd.reference(),
                    addrCmd.zipCode(), addrCmd.country());

            cost = shippingPricingService.calculateFor(address);
        } else {
            // Pedido 100% digital
            cost = new ShippingCost(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        // 7. Instanciar el Aggregate Root
        // Delegar al método Factory, el cual aplicará la invariante final
        // (requiresShipping && shippingAddress == null)
        Order order = Order.create(customerId, address, cost, validItems);

        // 8. Persistencia
        orderRepository.save(order);

        // 9. Publicar el Event
        OrderCreatedEvent event = new OrderCreatedEvent(

                order.getId().value().toString(),
                order.getOrderItems().stream().map(i -> {
                    return new OrderItemEvent(i.getBookId().value(), i.getQuantity().value());
                }).toList());

        domainEventPublisher.publish(event);

        // 10. Retornar ID crudo
        return order.getId().value();
    }
}