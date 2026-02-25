package com.example.myapp.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.myapp.entity.Book;
import com.example.myapp.exception.AppException;
import com.example.myapp.exception.ErrorCode;
import com.example.myapp.model.response.BookResponse;
import com.example.myapp.model.response.PageResponse;
import com.example.myapp.repository.*;
import com.example.myapp.service.*;
import com.example.myapp.util.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final PageMapper pageMapper;
    private final BookMapper bookMapper;

    @Override
    public PageResponse<BookResponse> getBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);
        return pageMapper.toPageResponse(bookPage, bookMapper::toResponse);
    }

    @Override
    public PageResponse<BookResponse> getBooksByCategory(Long categoryId, int page, int size) {

        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findByCategory_Id(categoryId, pageable);
        return pageMapper.toPageResponse(bookPage, bookMapper::toResponse);
    }

    @Override
    public PageResponse<BookResponse> getBooksByAuthor(Long authorId, int page, int size) {

        authorRepository.findById(authorId)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findByAuthor_Id(authorId, pageable);
        return pageMapper.toPageResponse(bookPage, bookMapper::toResponse);
    }

    @Override
    public PageResponse<BookResponse> searchBooks(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findByTitleContaining(keyword, pageable);
        return pageMapper.toPageResponse(bookPage, bookMapper::toResponse);
    }

    @Override
    public BookResponse getDetail(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
        return bookMapper.toResponse(book);
    }
}
