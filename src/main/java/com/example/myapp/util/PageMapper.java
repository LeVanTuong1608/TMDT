package com.example.myapp.util;

import org.springframework.data.domain.Page;

import com.example.myapp.entity.Book;
import com.example.myapp.model.response.BookResponse;
import com.example.myapp.model.response.PageResponse;

public class PageMapper {

    private PageMapper() {
    }

    /* ================= BOOK PAGE ================= */

    public static PageResponse<BookResponse> toBookPageResponse(Page<Book> page) {

        return PageResponse.<BookResponse>builder()
                .content(
                        page.getContent()
                                .stream()
                                .map(BookMapper::toResponse)
                                .toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}

