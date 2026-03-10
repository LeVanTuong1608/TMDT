package com.example.myapp.util;

import org.springframework.stereotype.Component;

import com.example.myapp.entity.Payment;
import com.example.myapp.model.response.PaymentResponse;

@Component
public class PaymentMapper {

    public PaymentResponse toResponse(Payment payment) {

        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .status(payment.getStatus())
                .message("payment completed")
                .build();
    }
}
