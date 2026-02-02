package com.example.myapp.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.myapp.service.*;
import com.example.myapp.util.BookMapper;
import com.example.myapp.util.PageMapper;
import com.example.myapp.repository.*;

import com.example.myapp.model.response.*;
import com.example.myapp.model.response.PageResponse;
import com.example.myapp.entity.*;
import com.example.myapp.exception.AppException;
import com.example.myapp.exception.ErrorCode;
import com.example.myapp.model.request.*;

@Service
public class AdminBookServiceImpl implements AdminBookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public AdminBookServiceImpl(BookRepository bookRepository,
            CategoryRepository categoryRepository,
            AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public BookResponse createBook(BookRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setImageUrl(request.getImageUrl());
        book.setPrice(request.getPrice());
        book.setDescription(request.getDescription());
        book.setPublisher(request.getPublisher());
        book.setPublicationYear(request.getPublicationYear());
        book.setDimensions(request.getDimensions());
        book.setCategory(category);
        book.setAuthor(author);

        return BookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest request) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

        book.setTitle(request.getTitle());
        book.setPrice(request.getPrice());
        book.setDescription(request.getDescription());

        return BookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new AppException(ErrorCode.BOOK_NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public PageResponse<BookResponse> searchBooks(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        return PageMapper.toBookPageResponse(books);
    }

    // private BookResponse mapToResponse(Book book) {
    // return BookResponse.builder()
    // .title(book.getTitle())
    // .imageUrl(book.getImageUrl())
    // .price(book.getPrice())
    // // .description(book.getDescription())
    // .publisher(book.getPublisher())
    // .publicationYear(book.getPublicationYear())
    // .dimensions(book.getDimensions())
    // .category(category)
    // .author(author)
    // .build();
    // }
}
