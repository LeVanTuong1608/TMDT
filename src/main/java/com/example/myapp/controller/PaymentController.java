package com.example.myapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.myapp.model.request.PaymentRequest;
import com.example.myapp.model.response.PaymentResponse;
import com.example.myapp.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestBody PaymentRequest request) {

        PaymentResponse response = paymentService.processPayment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}