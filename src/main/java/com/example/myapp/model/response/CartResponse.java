package com.example.myapp.model.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartResponse {
    private Long cartId;
    private String status;
    private List<CartItemResponse> items;
    private BigDecimal totalAmount;
}