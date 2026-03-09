package com.example.myapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.myapp.model.request.CreateOrderRequest;
import com.example.myapp.model.response.OrderResponse;
import com.example.myapp.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users/orders")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class OrderController {

    private final OrderService orderService;

    // @PostMapping
    // public ResponseEntity<OrderResponse> createOrder(
    // @Valid @RequestBody CreateOrderRequest request) {

    // return ResponseEntity.status(HttpStatus.CREATED)
    // .body(orderService.createOrder(request));
    // }

    @GetMapping("/my")
    public ResponseEntity<List<OrderResponse>> getMyOrders() {
        return ResponseEntity.ok(orderService.getMyOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

}
