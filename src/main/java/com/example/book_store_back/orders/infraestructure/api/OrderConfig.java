package com.example.book_store_back.orders.infraestructure.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.book_store_back.orders.application.ports.CatalogGateway;
import com.example.book_store_back.orders.application.ports.DomainEventPublisher;
import com.example.book_store_back.orders.application.ports.OrderRepository;
import com.example.book_store_back.orders.application.ports.ShippingPricingService;
import com.example.book_store_back.orders.application.usecases.CancelOrderInteractor;
import com.example.book_store_back.orders.application.usecases.CancelOrderUseCase;
import com.example.book_store_back.orders.application.usecases.CreateOrderInteractor;
import com.example.book_store_back.orders.application.usecases.CreateOrderUseCase;
import com.example.book_store_back.orders.application.usecases.GetOrderByIdInteractor;
import com.example.book_store_back.orders.application.usecases.GetOrderByIdUseCase;
import com.example.book_store_back.orders.application.usecases.MarkOrderAsAwaitingPaymentInteractor;
import com.example.book_store_back.orders.application.usecases.MarkOrderAsAwaitingPaymentUseCase;
import com.example.book_store_back.orders.application.usecases.MarkOrderAsPaidInteractor;
import com.example.book_store_back.orders.application.usecases.MarkOrderAsPaidUseCase;

@Configuration
public class OrderConfig {

    @Bean
    public CreateOrderUseCase createOrderUseCase(
            OrderRepository orderRepository,
            CatalogGateway catalogGateway,
            ShippingPricingService shippingPricingService,
            DomainEventPublisher domainEventPublisher) {
        return new CreateOrderInteractor(orderRepository, catalogGateway, shippingPricingService, domainEventPublisher);

    }

    @Bean
    public CancelOrderUseCase cancelOrderUseCase(
            OrderRepository orderRepository) {

        return new CancelOrderInteractor(orderRepository);

    }

    @Bean
    public GetOrderByIdUseCase getOrderByIdUseCase(OrderRepository orderRepository) {

        return new GetOrderByIdInteractor(orderRepository);
    }

    @Bean
    public MarkOrderAsAwaitingPaymentUseCase markOrderAsAwaitingPaymentUseCase(OrderRepository orderRepository) {
        return new MarkOrderAsAwaitingPaymentInteractor(orderRepository);
    }

    @Bean 
    public MarkOrderAsPaidUseCase markOrderAsPaidUseCase(OrderRepository orderRepository){
        return new MarkOrderAsPaidInteractor(orderRepository);
    }

}
