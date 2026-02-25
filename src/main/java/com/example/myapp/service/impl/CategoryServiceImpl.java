package com.example.myapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.myapp.model.response.CategoryResponse;
import com.example.myapp.repository.CategoryRepository;
import com.example.myapp.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(category -> CategoryResponse.builder()
                        .id(category.getId())
                        .categoryName(category.getCategoryName())
                        .build())
                .collect(Collectors.toList());
    }
}
