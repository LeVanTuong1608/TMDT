package com.example.myapp.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.myapp.service.PaymentService;
import com.example.myapp.util.PaymentMapper;

import lombok.RequiredArgsConstructor;
import com.example.myapp.model.response.PaymentResponse;
import com.example.myapp.repository.PaymentRepository;
import com.example.myapp.repository.OrderRepository;
import com.example.myapp.exception.*;
import com.example.myapp.entity.*;
import com.example.myapp.model.request.*;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        Order order = orderRepository.findByIdAndUserId(request.getOrderId(), userId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getStatus().equals("paid")) {
            throw new AppException(ErrorCode.ORDER_ALREADY_PAID);
        }
        String transactionId = UUID.randomUUID().toString();
        String paymentStatus = "pending";
        Payment payment = Payment.builder()
                .order(order)
                .transactionId(transactionId)
                .method(request.getPaymentMethod())
                .status(paymentStatus)
                .amount(order.getTotalAmount())
                .paymentDate(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);
        order.setStatus("paid");
        orderRepository.save(order);
        return paymentMapper.toResponse(payment);
    }
}
