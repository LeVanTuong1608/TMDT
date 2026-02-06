package com.example.myapp.service.impl;

import com.example.myapp.model.response.dashboard.*;
import com.example.myapp.service.AdminDashboardService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Override
    public DashboardSummaryResponse getSummary() {
        return new DashboardSummaryResponse(
                1200, // totalUsers
                350, // totalBooks
                980, // totalOrders
                15, // pendingOrders
                125_000_000L // totalRevenue
        );
    }

    @Override
    public List<RevenueResponse> getRevenueByYear(int year) {
        return List.of(
                new RevenueResponse(1, 12_000_000L),
                new RevenueResponse(2, 18_000_000L),
                new RevenueResponse(3, 25_000_000L));
    }

    @Override
    public List<RecentOrderResponse> getRecentOrders() {
        return List.of(
                new RecentOrderResponse(1L, "ORD001", 2_500_000L, "PENDING"),
                new RecentOrderResponse(2L, "ORD002", 1_200_000L, "SUCCESS"));
    }

    @Override
    public List<TopBookResponse> getTopBooks() {
        return List.of(
                new TopBookResponse(1L, "Clean Code", 120),
                new TopBookResponse(2L, "Spring Boot", 95));
    }

    @Override
    public List<DashboardAlertResponse> getAlerts() {
        return List.of(
                new DashboardAlertResponse("LOW_STOCK", "Sách Clean Code sắp hết"),
                new DashboardAlertResponse("ORDER_FAILED", "Đơn ORD009 thanh toán lỗi"));
    }
}
