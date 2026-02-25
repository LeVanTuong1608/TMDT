package com.example.myapp.service;

import java.util.List;

import com.example.myapp.model.response.CategoryResponse;

public interface CategoryService {
    List<CategoryResponse> getAll();

}
