package com.order.mgmt.service.controller;

import com.order.mgmt.service.dto.CreateOrderRequest;
import com.order.mgmt.service.dto.UpdateStatusRequest;
import com.order.mgmt.service.entities.Order;
import com.order.mgmt.service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order order = service.createOrder(request);
        URI location = URI.create("/orders/" + order.getOrderId());
        return ResponseEntity.created(location).body(order);
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(service.getOrderById(orderId));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderId, @Valid @RequestBody UpdateStatusRequest updateRequest) {
        return ResponseEntity.ok(service.updateOrderStatus(orderId, updateRequest.getOrderStatus()));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(service.getAllOrders());
    }
}
