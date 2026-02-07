package com.example.myapp.controller.admin;

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

import com.example.myapp.model.request.CategoryRequest;
import com.example.myapp.model.response.CategoryResponse;
import com.example.myapp.model.response.PageResponse;
import com.example.myapp.service.AdminCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/categories")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin - Categories")
@Validated
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    public AdminCategoryController(AdminCategoryService adminCategoryService) {
        this.adminCategoryService = adminCategoryService;
    }

    // ================= CREATE =================
    @PostMapping
    @Operation(summary = "Create new category")
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest request) {

        CategoryResponse response = adminCategoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    @Operation(summary = "Update category")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {

        CategoryResponse response = adminCategoryService.updateCategory(id, request);
        return ResponseEntity.ok(response);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // ================= DETAIL =================
    @GetMapping("/{id}")
    @Operation(summary = "Get detail of category")
    public ResponseEntity<CategoryResponse> getDetail(@PathVariable Long id) {
        CategoryResponse response = adminCategoryService.getDetail(id);
        return ResponseEntity.ok(response);
    }

    // ================= SEARCH =================
    @GetMapping("/search")
    public ResponseEntity<PageResponse<CategoryResponse>> search(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<CategoryResponse> response = adminCategoryService.searchCategories(keyword, page, size);
        return ResponseEntity.ok(response);
    }

}
