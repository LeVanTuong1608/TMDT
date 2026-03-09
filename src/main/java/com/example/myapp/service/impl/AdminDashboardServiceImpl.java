package com.example.myapp.service.impl;

import com.example.myapp.model.response.DashboardSummaryResponse;
import com.example.myapp.repository.OrderRepository;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.service.AdminDashboardService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public AdminDashboardServiceImpl(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public DashboardSummaryResponse getSummary() {
        long totalUsers = userRepository.count();
        long totalOrders = orderRepository.count();
        BigDecimal totalRevenue = orderRepository.sumTotalAmount();
        return new DashboardSummaryResponse(totalUsers, totalOrders, totalRevenue);
    }
}