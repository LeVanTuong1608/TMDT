package com.example.myapp.service;


import com.example.myapp.model.response.BookResponse;
import com.example.myapp.model.response.PageResponse;

public interface BookService {

    PageResponse<BookResponse> getBooks(int page, int size);

    PageResponse<BookResponse> getBooksByCategory(Long categoryId, int page, int size);

    PageResponse<BookResponse> getBooksByAuthor(Long authorId, int page, int size);
}
