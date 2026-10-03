package com.example.book_store_back.orders.infraestructure.api;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.book_store_back.orders.application.dtos.CancelOrderCommand;
import com.example.book_store_back.orders.application.dtos.CreateOrderCommand;
import com.example.book_store_back.orders.application.dtos.OrderDetailsResult;
import com.example.book_store_back.orders.application.usecases.CancelOrderUseCase;
import com.example.book_store_back.orders.application.usecases.CreateOrderUseCase;
import com.example.book_store_back.orders.application.usecases.GetOrderByIdUseCase;
import com.example.book_store_back.orders.infraestructure.api.dto.request.CreateOrderRequest;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;

    public OrderController(
            CreateOrderUseCase createOrderUseCase,
            CancelOrderUseCase cancelOrderUseCase,
            GetOrderByIdUseCase getOrderByIdUseCase
) {
        this.createOrderUseCase = createOrderUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
        this.getOrderByIdUseCase = getOrderByIdUseCase;

    }

    @PostMapping()
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderRequest request, @AuthenticationPrincipal Jwt jwt) {

        String safeCustomerId = jwt.getSubject();

        CreateOrderCommand command = request.toCommand(safeCustomerId);
        UUID orderId = createOrderUseCase.execute(command);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable String id) {
        CancelOrderCommand command = new CancelOrderCommand(id, "CANCELLED_BY_USER");
        cancelOrderUseCase.execute(command);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailsResult> getOrderById(@PathVariable String id) {
        OrderDetailsResult result = getOrderByIdUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

}
