
package com.example.myapp.controller.admin;

import com.example.myapp.model.response.DashboardOverviewResponse;
import com.example.myapp.model.response.DashboardResponse;
import com.example.myapp.model.response.DashboardRevenueResponse;
import com.example.myapp.model.response.DashboardSalesResponse;
import com.example.myapp.model.response.DashboardTopAuthorResponse;
import com.example.myapp.model.response.DashboardTopBookResponse;
import com.example.myapp.model.response.DashboardTopCategoryResponse;
import com.example.myapp.model.response.DashboardTopCityResponse;
import com.example.myapp.model.response.DashboardTopCommentResponse;
import com.example.myapp.model.response.DashboardTopCountryResponse;
import com.example.myapp.model.response.DashboardTopOrderResponse;
import com.example.myapp.model.response.DashboardTopPaymentMethodResponse;
import com.example.myapp.model.response.DashboardTopProductResponse;
import com.example.myapp.model.response.DashboardTopReviewResponse;
import com.example.myapp.model.response.DashboardTopUserResponse;
import com.example.myapp.service.AdminDashboardService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/dashboard")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin - Dashboard")
@Validated
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    public AdminDashboardController(AdminDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/overview")
    public ResponseEntity<DashboardOverviewResponse> getOverview() {
        DashboardOverviewResponse response = dashboardService.getOverview();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/revenue")
    public ResponseEntity<DashboardRevenueResponse> getRevenue() {
        DashboardRevenueResponse response = dashboardService.getRevenue();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sales")
    public ResponseEntity<DashboardSalesResponse> getSales() {
        DashboardSalesResponse response = dashboardService.getSales();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-books")
    public ResponseEntity<List<DashboardTopBookResponse>> getTopBooks() {
        List<DashboardTopBookResponse> response = dashboardService.getTopBooks();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-authors")
    public ResponseEntity<List<DashboardTopAuthorResponse>> getTopAuthors() {
        List<DashboardTopAuthorResponse> response = dashboardService.getTopAuthors();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-categories")
    public ResponseEntity<List<DashboardTopCategoryResponse>> getTopCategories() {
        List<DashboardTopCategoryResponse> response = dashboardService.getTopCategories();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-users")
    public ResponseEntity<List<DashboardTopUserResponse>> getTopUsers() {
        List<DashboardTopUserResponse> response = dashboardService.getTopUsers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-countries")
    public ResponseEntity<List<DashboardTopCountryResponse>> getTopCountries() {
        List<DashboardTopCountryResponse> response = dashboardService.getTopCountries();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-cities")
    public ResponseEntity<List<DashboardTopCityResponse>> getTopCities() {
        List<DashboardTopCityResponse> response = dashboardService.getTopCities();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-payment-methods")
    public ResponseEntity<List<DashboardTopPaymentMethodResponse>> getTopPaymentMethods() {
        List<DashboardTopPaymentMethodResponse> response = dashboardService.getTopPaymentMethods();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-orders")
    public ResponseEntity<List<DashboardTopOrderResponse>> getTopOrders() {
        List<DashboardTopOrderResponse> response = dashboardService.getTopOrders();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<DashboardTopProductResponse>> getTopProducts() {
        List<DashboardTopProductResponse> response = dashboardService.getTopProducts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-reviews")
    public ResponseEntity<List<DashboardTopReviewResponse>> getTopReviews() {
        List<DashboardTopReviewResponse> response = dashboardService.getTopReviews();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-comments")
    public ResponseEntity<List<DashboardTopCommentResponse>> getTopComments() {
        List<DashboardTopCommentResponse> response = dashboardService.getTopComments();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        DashboardResponse response = dashboardService.getDashboard();
        return ResponseEntity.ok(response);
    }

}
