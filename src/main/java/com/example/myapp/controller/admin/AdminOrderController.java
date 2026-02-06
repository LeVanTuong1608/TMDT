package com.example.myapp.controller.admin;

@RestController
@RequestMapping("/api/admin/orders")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    public AdminOrderController(AdminOrderService adminOrderService) {
        this.adminOrderService = adminOrderService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<OrderResponse>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(adminOrderService.getOrders(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getDetail(@PathVariable Long id) {
        return ResponseEntity.ok(adminOrderService.getDetail(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        adminOrderService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}
