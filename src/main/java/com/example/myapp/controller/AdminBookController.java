package com.example.myapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.myapp.service.*;
import com.example.myapp.model.response.*;
import com.example.myapp.model.request.*;

@RestController
@RequestMapping("/api/admin/books")
@PreAuthorize("hasRole('ADMIN')")
public class AdminBookController {

    private final AdminBookService adminBookService;

    public AdminBookController(AdminBookService adminBookService) {
        this.adminBookService = adminBookService;
    }

    @PostMapping
    public BookResponse create(@RequestBody BookRequest request) {
        return adminBookService.createBook(request);
    }

    @PutMapping("/{id}")
    public BookResponse update(@PathVariable Long id,
            @RequestBody BookRequest request) {
        return adminBookService.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        adminBookService.deleteBook(id);
    }

    @GetMapping("/search")
    public PageResponse<BookResponse> search(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size) {
        return adminBookService.searchBooks(keyword, page, size);
    }
}
