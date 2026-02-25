package com.example.myapp.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.myapp.entity.Order;
import com.example.myapp.entity.OrderItem;
import com.example.myapp.model.response.OrderResponse;

@Component
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    public OrderResponse toResponse(Order order, List<OrderItem> items) {

        return OrderResponse.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .items(items.stream().map(orderItemMapper::toResponse).toList())
                .build();
    }
}
