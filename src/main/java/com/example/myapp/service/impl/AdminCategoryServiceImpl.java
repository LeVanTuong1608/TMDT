package com.example.myapp.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.example.myapp.entity.Category;
import com.example.myapp.exception.AppException;
import com.example.myapp.exception.ErrorCode;
import com.example.myapp.model.request.CategoryRequest;
import com.example.myapp.model.response.CategoryResponse;
import com.example.myapp.model.response.PageResponse;
import com.example.myapp.repository.*;
import com.example.myapp.service.AdminCategoryService;
import com.example.myapp.util.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final PageMapper pageMapper;

    @Override
    public PageResponse<CategoryResponse> getCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return pageMapper.toPageResponse(categoryPage, categoryMapper::toResponse);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        category = categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        category.setCategoryName(request.getCategoryName());
        category = categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
    }

    @Override
    public PageResponse<CategoryResponse> searchCategories(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findByCategoryNameContaining(keyword, pageable);
        return pageMapper.toPageResponse(categoryPage, categoryMapper::toResponse);
    }

}
