
package com.example.myapp.controller.admin;

import com.example.myapp.model.response.dashboard.*;
import com.example.myapp.service.AdminDashboardService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    public AdminDashboardController(AdminDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // ===== 1. SUMMARY =====
    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryResponse> summary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }

    // ===== 2. REVENUE =====
    @GetMapping("/revenue")
    public ResponseEntity<List<RevenueResponse>> revenue(
            @RequestParam int year) {
        return ResponseEntity.ok(dashboardService.getRevenueByYear(year));
    }

    // ===== 3. RECENT ORDERS =====
    @GetMapping("/recent-orders")
    public ResponseEntity<List<RecentOrderResponse>> recentOrders() {
        return ResponseEntity.ok(dashboardService.getRecentOrders());
    }

    // ===== 4. TOP BOOKS =====
    @GetMapping("/top-books")
    public ResponseEntity<List<TopBookResponse>> topBooks() {
        return ResponseEntity.ok(dashboardService.getTopBooks());
    }

    // ===== 5. ALERTS =====
    @GetMapping("/alerts")
    public ResponseEntity<List<DashboardAlertResponse>> alerts() {
        return ResponseEntity.ok(dashboardService.getAlerts());
    }
}
