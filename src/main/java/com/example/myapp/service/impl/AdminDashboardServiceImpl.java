package com.example.myapp.service.impl;

import com.example.myapp.model.response.dashboard.*;
import com.example.myapp.service.AdminDashboardService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {
    @Override
    public DashboardSummaryResponse getSummary() {
        return null;
    }

    @Override
    public List<RevenueResponse> getRevenueByYear(int year) {
        return List.of();
    }

    @Override
    public List<RecentOrderResponse> getRecentOrders() {
        return List.of();
    }

    @Override
    public List<TopBookResponse> getTopBooks() {
        return List.of();
    }

    @Override
    public List<DashboardAlertResponse> getAlerts() {
        return List.of();
    }

}
