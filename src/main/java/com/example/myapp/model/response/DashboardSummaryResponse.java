package com.example.myapp.model.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardSummaryResponse {
    private long totalUsers;
    private long totalOrders;
    private BigDecimal totalRevenue;

    public DashboardSummaryResponse(long totalUsers, long totalOrders, BigDecimal totalRevenue) {
        this.totalUsers = totalUsers;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
    }
}
