package com.example.myapp.service;

import com.example.myapp.model.request.PaymentRequest;
import com.example.myapp.model.response.PaymentResponse;
import java.util.List;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest request);

}
