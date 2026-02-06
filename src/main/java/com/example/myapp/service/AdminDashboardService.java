package com.example.myapp.service;

import com.example.myapp.model.response.dashboard.*;

import java.util.List;

public interface AdminDashboardService {

    DashboardSummaryResponse getSummary();

    List<RevenueResponse> getRevenueByYear(int year);

    List<RecentOrderResponse> getRecentOrders();

    List<TopBookResponse> getTopBooks();

    List<DashboardAlertResponse> getAlerts();
}
