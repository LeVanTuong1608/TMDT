
package com.example.myapp.controller.admin;

import com.example.myapp.model.request.BookRequest;
import com.example.myapp.model.response.BookResponse;
import com.example.myapp.model.response.PageResponse;
import com.example.myapp.service.AdminBookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/books")
// @PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin - Book")
@Validated
public class AdminBookController {

    private final AdminBookService adminBookService;

    public AdminBookController(AdminBookService adminBookService) {
        this.adminBookService = adminBookService;
    }

    // ================= CREATE =================
    @PostMapping
    @Operation(summary = "Create new book")
    public ResponseEntity<BookResponse> createBook(
            @Valid @RequestBody BookRequest request) {

        BookResponse response = adminBookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    @Operation(summary = "Update book")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest request) {

        BookResponse response = adminBookService.updateBook(id, request);
        return ResponseEntity.ok(response);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminBookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // ================= DETAIL =================
    @GetMapping("/{id}")
    @Operation(summary = "Get detail of book")
    public ResponseEntity<BookResponse> getDetail(@PathVariable Long id) {
        BookResponse response = adminBookService.getDetail(id);
        return ResponseEntity.ok(response);
    }

    // ================= SEARCH =================
    @GetMapping("/search")
    public ResponseEntity<PageResponse<BookResponse>> search(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<BookResponse> response = adminBookService.searchBooks(keyword, page, size);

        return ResponseEntity.ok(response);
    }
}
