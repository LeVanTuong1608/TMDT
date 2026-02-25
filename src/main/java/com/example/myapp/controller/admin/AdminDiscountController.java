// package com.example.myapp.controller.admin;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.myapp.model.request.DiscountRequest;
// import com.example.myapp.model.response.DiscountResponse;
// import com.example.myapp.model.response.PageResponse;
// import com.example.myapp.service.AdminDiscountService;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.validation.Valid;

// @RestController
// @RequestMapping("/api/admin/books")
// @PreAuthorize("hasRole('ADMIN')")
// @Tag(name = "Admin - Books")
// @Validated
// public class AdminDiscountController {
//     private final AdminDiscountService adminDiscountService;

//     public AdminDiscountController(AdminDiscountService adminDiscountService) {
//         this.adminDiscountService = adminDiscountService;
//     }

//     @PostMapping
//     @Operation(summary = "Create new discount")
//     public ResponseEntity<DiscountResponse> createDiscount(
//             @Valid @RequestBody DiscountRequest request) {

//         DiscountResponse response = adminDiscountService.createDiscount(request);
//         return ResponseEntity.status(HttpStatus.CREATED).body(response);
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update discount")
//     public ResponseEntity<DiscountResponse> updateDiscount(
//             @PathVariable Long id,
//             @Valid @RequestBody DiscountRequest request) {

//         DiscountResponse response = adminDiscountService.updateDiscount(id, request);
//         return ResponseEntity.ok(response);
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete discount")
//     public ResponseEntity<Void> delete(@PathVariable Long id) {
//         adminDiscountService.deleteDiscount(id);
//         return ResponseEntity.noContent().build();
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get detail of discount")
//     public ResponseEntity<DiscountResponse> getDetail(@PathVariable Long id) {
//         DiscountResponse response = adminDiscountService.getDetail(id);
//         return ResponseEntity.ok(response);
//     }

//     @GetMapping("/search")
//     public ResponseEntity<PageResponse<DiscountResponse>> search(
//             @RequestParam(required = false, defaultValue = "") String keyword,
//             @RequestParam(defaultValue = "0") int page,
//             @RequestParam(defaultValue = "10") int size) {
//         PageResponse<DiscountResponse> response = adminDiscountService.searchDiscounts(keyword, page, size);
//         return ResponseEntity.ok(response);
//     }

// }
