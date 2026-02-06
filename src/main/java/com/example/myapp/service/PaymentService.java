package com.example.myapp.service;

import com.example.myapp.model.response.PaymentResponse;
import java.util.List;

public interface PaymentService {
    PaymentResponse processPayment(Long orderId, String paymentMethod);

    List<PaymentResponse> getPaymentsByOrder(Long orderId);
}
