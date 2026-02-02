package com.example.myapp.service;

import java.util.*;
import com.example.myapp.model.response.*;

public interface CartService {

    void addToCart(Long bookId, int quantity);

    void removeFromCart(Long bookId);

    // List<CartItemResponse> getMyCart();

    CartResponse getMyCart();
}