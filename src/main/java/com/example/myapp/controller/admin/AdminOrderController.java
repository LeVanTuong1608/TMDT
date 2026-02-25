package com.example.myapp.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.model.request.OrderRequest;
import com.example.myapp.model.response.OrderResponse;
import com.example.myapp.model.response.PageResponse;
import com.example.myapp.service.AdminOrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/orders")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin - Orders")
@Validated
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    public AdminOrderController(AdminOrderService adminOrderService) {
        this.adminOrderService = adminOrderService;
    }

    // ================= CREATE =================
    // @PostMapping
    // @Operation(summary = "Create new order")
    // public ResponseEntity<OrderResponse> createOrder(
    // @Valid @RequestBody OrderRequest request) {

    // OrderResponse response = adminOrderService.createOrder(request);
    // return ResponseEntity.status(HttpStatus.CREATED).body(response);
    // }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    @Operation(summary = "Update status order")
    public ResponseEntity<OrderResponse> updateOrderstatus(
            @PathVariable Long id,
            @Valid @RequestBody OrderRequest request) {

        OrderResponse response = adminOrderService.updateOrderStatus(id, request.getStatus());

        return ResponseEntity.ok(response);
    }

    // ================= DELETE =================
    // @DeleteMapping("/{id}")
    // @Operation(summary = "Delete order")
    // public ResponseEntity<Void> delete(@PathVariable Long id) {
    // adminOrderService.deleteOrder(id);
    // return ResponseEntity.noContent().build();
    // }

    // ================= DETAIL =================
    @GetMapping("/{id}")
    @Operation(summary = "Get detail of order")
    public ResponseEntity<OrderResponse> getDetail(@PathVariable Long id) {
        OrderResponse response = adminOrderService.getDetail(id);
        return ResponseEntity.ok(response);
    }

    // ================= SEARCH =================
    @GetMapping("/search")
    public ResponseEntity<PageResponse<OrderResponse>> search(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<OrderResponse> response = adminOrderService.searchOrders(keyword, page, size);
        return ResponseEntity.ok(response);
    }

}
