package com.example.myapp.service;

import org.springframework.stereotype.Service;

import com.example.myapp.model.response.OrderResponse;
import com.example.myapp.model.response.PageResponse;

public interface AdminOrderService {
    PageResponse<OrderResponse> getAllOrders(int page, int size);

    OrderResponse updateOrderStatus(Long orderId, String status);
}
