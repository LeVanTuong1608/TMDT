package com.example.myapp.service;

import com.example.myapp.model.request.CategoryRequest;
import com.example.myapp.model.response.CategoryResponse;
import com.example.myapp.model.response.PageResponse;

public interface AdminCategoryService {
    PageResponse<CategoryResponse> getCategories(int page, int size);

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);

    PageResponse<CategoryResponse> searchCategories(String keyword, int page, int size);

    // CategoryResponse getDetail(Long id);

}
