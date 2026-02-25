package com.example.myapp.util;

import org.springframework.stereotype.Component;

import com.example.myapp.entity.OrderItem;
import com.example.myapp.model.response.OrderItemResponse;

@Component
public class OrderItemMapper {

    public OrderItemResponse toResponse(OrderItem item) {

        return OrderItemResponse.builder()
                .id(item.getId())
                .bookId(item.getBook().getId())
                .bookTitle(item.getBook().getTitle())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .subtotal(item.getSubtotal())
                .build();
    }
}
