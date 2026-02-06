package com.example.myapp.util;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.myapp.entity.Book;
import com.example.myapp.model.response.BookResponse;
import com.example.myapp.model.response.PageResponse;

import java.util.function.Function;

@Component
public class PageMapper {

    // private PageMapper() {
    // }

    /* ================= BOOK PAGE ================= */

    public <T, R> PageResponse<R> toPageResponse(Page<T> page, Function<T, R> mapper) {

        return PageResponse.<R>builder()
                .content(
                        page.getContent()
                                .stream()
                                .map(mapper)
                                .toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
