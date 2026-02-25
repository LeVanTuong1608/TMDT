package com.example.myapp.service.impl;

import com.example.myapp.entity.Order;
import com.example.myapp.exception.AppException;
import com.example.myapp.exception.ErrorCode;
import com.example.myapp.model.response.OrderResponse;
import com.example.myapp.model.response.PageResponse;
import com.example.myapp.repository.OrderRepository;
import com.example.myapp.service.AdminOrderService;
import com.example.myapp.util.OrderMapper;
import com.example.myapp.util.PageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {
    private final OrderRepository orderRepository;
    private final PageMapper pageMapper;
    private final OrderMapper orderMapper;

    @Override
    public PageResponse<OrderResponse> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return pageMapper.toPageResponse(orderPage, order -> orderMapper.toResponse(order, order.getOrderItems()));
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        order.setStatus(status);
        order = orderRepository.save(order);
        return orderMapper.toResponse(order, order.getOrderItems());
    }

    @Override
    public PageResponse<OrderResponse> searchOrders(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // Assuming the repository has a method to search by status or user full name
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return pageMapper.toPageResponse(orderPage, order -> orderMapper.toResponse(order, order.getOrderItems()));
    }

    @Override
    public OrderResponse getDetail(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return orderMapper.toResponse(order, order.getOrderItems());
    }

}
