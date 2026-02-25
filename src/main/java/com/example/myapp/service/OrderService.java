package com.example.myapp.service;

import com.example.myapp.model.response.OrderResponse;
import java.util.List;

public interface OrderService {
    OrderResponse placeOrder();

    List<OrderResponse> getMyOrders();

    OrderResponse getOrderById(Long orderId);
    
}
