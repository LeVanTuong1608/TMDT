package com.example.myapp.util;

import java.math.BigDecimal;
import java.util.List;

import com.example.myapp.entity.Cart;
import com.example.myapp.entity.CartItem;
import com.example.myapp.model.response.CartItemResponse;
import com.example.myapp.model.response.CartResponse;

public class CartMapper {

        private CartMapper() {
        }

        public static CartResponse toResponse(Cart cart, List<CartItem> cartItems) {

                List<CartItemResponse> items = cartItems.stream()
                                .map(CartMapper::toItemResponse)
                                .toList();

                BigDecimal totalAmount = items.stream()
                                .map(CartItemResponse::getTotalPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                return CartResponse.builder()
                                .cartId(cart.getId())
                                .status(cart.getStatus())
                                .items(items)
                                .totalAmount(totalAmount)
                                .build();
        }

        public static CartItemResponse toItemResponse(CartItem item) {

                BigDecimal totalPrice = item.getPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity()));

                return CartItemResponse.builder()
                                .cartItemId(item.getId())
                                .bookId(item.getBook().getId())
                                .bookTitle(item.getBook().getTitle())
                                .imageUrl(item.getBook().getImageUrl())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .totalPrice(totalPrice)
                                .build();
        }
}
