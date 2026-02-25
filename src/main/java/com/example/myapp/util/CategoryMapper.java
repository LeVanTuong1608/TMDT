package com.example.myapp.util;

import org.springframework.stereotype.Component;

import com.example.myapp.entity.Category;
import com.example.myapp.model.response.CategoryResponse;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .build();
    }
}
