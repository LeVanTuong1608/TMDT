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

import com.example.myapp.model.request.AuthorRequest;
import com.example.myapp.model.response.AuthorResponse;
import com.example.myapp.model.response.PageResponse;
import com.example.myapp.service.AdminAuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/authors")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin - Authors")
@Validated
public class AdminAuthorController {
    private final AdminAuthorService adminAuthorService;

    public AdminAuthorController(AdminAuthorService adminAuthorService) {
        this.adminAuthorService = adminAuthorService;
    }

    // ================= CREATE =================
    @PostMapping
    @Operation(summary = "Create new author")
    public ResponseEntity<AuthorResponse> createAuthor(
            @Valid @RequestBody AuthorRequest request) {

        AuthorResponse response = adminAuthorService.createAuthor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    @Operation(summary = "Update author")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequest request) {

        AuthorResponse response = adminAuthorService.updateAuthor(id, request);
        return ResponseEntity.ok(response);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete author")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminAuthorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    // ================= DETAIL =================
    // @GetMapping("/{id}")
    // @Operation(summary = "Get detail of author")
    // public ResponseEntity<AuthorResponse> getDetail(@PathVariable Long id) {
    //     AuthorResponse response = adminAuthorService.getDetail(id);
    //     return ResponseEntity.ok(response);
    // }

    // ================= SEARCH =================
    @GetMapping("/search")
    public ResponseEntity<PageResponse<AuthorResponse>> search(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<AuthorResponse> response = adminAuthorService.searchAuthors(keyword, page, size);
        return ResponseEntity.ok(response);
    }

}
