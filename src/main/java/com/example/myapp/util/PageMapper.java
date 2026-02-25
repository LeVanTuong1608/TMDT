package com.example.myapp.util;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.myapp.model.response.PageResponse;

import java.util.function.Function;

@Component
public class PageMapper {

    // private PageMapper() {
    // }

    // public <T, R> PageResponse<R> toPageResponse(Page<T> page, Function<T, R>
    // mapper) {

    // return PageResponse.<R>builder()
    // .content(
    // page.getContent()
    // .stream()
    // .map(mapper)
    // .toList())
    // .page(page.getNumber() + 1)
    // .size(page.getSize())
    // .totalElements(page.getTotalElements())
    // .totalPages(page.getTotalPages())
    // .last(page.isLast())
    // .build();
    // }
    public <T, R> PageResponse<R> toPageResponse(Page<T> page, Function<T, R> mapper) {

        return new PageResponse<>(
                page.getNumber() + 1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getContent()
                        .stream()
                        .map(mapper)
                        .toList());
    }
}
