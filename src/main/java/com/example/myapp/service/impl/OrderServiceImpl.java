package com.example.myapp.service.impl;

import com.example.myapp.entity.*;
import com.example.myapp.exception.AppException;
import com.example.myapp.exception.ErrorCode;
import com.example.myapp.exception.ResourceNotFoundException;
import com.example.myapp.model.response.OrderItemResponse;
import com.example.myapp.model.response.OrderResponse;
import com.example.myapp.repository.*;
import com.example.myapp.service.OrderService;
import com.example.myapp.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

        private final OrderRepository orderRepository;
        private final CartRepository cartRepository;
        private final CartItemRepository cartItemRepository;
        private final OrderItemRepository orderItemRepository;
        private final UserfrofileRepository userRepository;

        @Override
        public OrderResponse placeOrder() {
                String userEmail = SecurityUtil.getCurrentUserLogin();
                User user = userRepository.findByEmail(userEmail)
                                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                // new ResourceNotFoundException("User not found"));

                Cart cart = cartRepository.findByUserAndStatus(user, "active")
                                .orElseThrow(() -> new AppException(ErrorCode.ACTIVE_CART_NOT_FOUND));
                // new ResourceNotFoundException("Active cart not found"));

                List<CartItem> cartItems = cartItemRepository.findByCart(cart);
                if (cartItems.isEmpty()) {
                        throw new IllegalStateException("Cart is empty");
                }

                // Calculate total
                BigDecimal total = cartItems.stream()
                                .map(item -> item.getBook().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                // Create order
                Order order = new Order();
                order.setUser(user);
                order.setOrderDate(LocalDateTime.now());
                order.setTotalAmount(total);
                order.setStatus("pending");

                order = orderRepository.save(order);

                // Create order items
                List<OrderItem> orderItems = cartItems.stream()
                                .map(cartItem -> {
                                        OrderItem orderItem = new OrderItem();
                                        // orderItem.setOrder(order);
                                        orderItem.setBook(cartItem.getBook());
                                        orderItem.setQuantity(cartItem.getQuantity());
                                        orderItem.setPrice(cartItem.getBook().getPrice());
                                        return orderItem;
                                })
                                .collect(Collectors.toList());

                orderItemRepository.saveAll(orderItems);

                // Clear cart
                cartItemRepository.deleteAll(cartItems);
                cart.setStatus("completed");
                cartRepository.save(cart);

                return mapToResponse(order, orderItems);
        }

        @Override
        public List<OrderResponse> getMyOrders() {
                String userEmail = SecurityUtil.getCurrentUserLogin();
                User user = userRepository.findByEmail(userEmail)
                                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                // new ResourceNotFoundException("User not found"));

                List<Order> orders = orderRepository.findByUser(user);
                return orders.stream()
                                .map(order -> {
                                        List<OrderItem> items = orderItemRepository.findByOrder(order);
                                        return mapToResponse(order, items);
                                })
                                .collect(Collectors.toList());
        }

        @Override
        public OrderResponse getOrderById(Long orderId) {
                String userEmail = SecurityUtil.getCurrentUserLogin();
                User user = userRepository.findByEmail(userEmail)
                                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                // new ResourceNotFoundException("User not found"));

                Order order = orderRepository.findById(orderId)
                                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
                // new ResourceNotFoundException("Order not found"));

                if (!order.getUser().getId().equals(user.getId())) {
                        throw new IllegalStateException("Access denied");
                }

                List<OrderItem> items = orderItemRepository.findByOrder(order);
                return mapToResponse(order, items);
        }

        private OrderResponse mapToResponse(Order order, List<OrderItem> items) {
                List<OrderItemResponse> itemResponses = items.stream()
                                .map(item -> OrderItemResponse.builder()
                                                .bookId(item.getBook().getId())
                                                .bookTitle(item.getBook().getTitle())
                                                .quantity(item.getQuantity())
                                                .price(item.getPrice())
                                                .build())
                                .collect(Collectors.toList());

                return OrderResponse.builder()
                                .id(order.getId())
                                .userEmail(order.getUser().getEmail())
                                .orderDate(order.getOrderDate())
                                .totalAmount(order.getTotalAmount())
                                .status(order.getStatus())
                                .items(itemResponses)
                                .build();
        }
}
