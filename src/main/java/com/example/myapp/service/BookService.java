package com.example.myapp.service;

import com.example.myapp.model.response.BookResponse;
import com.example.myapp.model.response.PageResponse;

public interface BookService {

    PageResponse<BookResponse> getBooks(int page, int size);

    PageResponse<BookResponse> getBooksByCategory(Long categoryId, int page, int size);

    PageResponse<BookResponse> getBooksByAuthor(Long authorId, int page, int size);

    PageResponse<BookResponse> searchBooks(String keyword, int page, int size);

    BookResponse getDetail(Long id);



}
// PageResponse<BookResponse> getBooks(int page, int size);

//     BookResponse createBook(BookRequest request);

//     BookResponse updateBook(Long id, BookRequest request);

//     void deleteBook(Long id);

//     PageResponse<BookResponse> searchBooks(String keyword, int page, int size);

//     BookResponse getDetail(Long id);
// }