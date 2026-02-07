package com.example.myapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.myapp.model.request.CreatePaymentRequest;
import com.example.myapp.model.response.PaymentResponse;
import com.example.myapp.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody CreatePaymentRequest request) {

        return ResponseEntity.ok(
                paymentService.createPayment(request));
    }

    @PostMapping("/callback")
    public ResponseEntity<Void> handleCallback(
            HttpServletRequest request) {

        paymentService.handleCallback(request);
        return ResponseEntity.ok().build();
    }

}
