package com.example.myapp.service;

import com.example.myapp.model.request.BookRequest;
import com.example.myapp.model.response.*;

public interface AdminBookService {

    BookResponse createBook(BookRequest request);

    BookResponse updateBook(Long id, BookRequest request);

    void deleteBook(Long id);

    PageResponse<BookResponse> searchBooks(String keyword, int page, int size);
}
