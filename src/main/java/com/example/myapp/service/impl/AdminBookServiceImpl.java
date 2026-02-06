package com.example.myapp.service.impl;

import com.example.myapp.entity.Author;
import com.example.myapp.entity.Book;
import com.example.myapp.entity.Category;
import com.example.myapp.exception.ResourceNotFoundException;
import com.example.myapp.model.request.BookRequest;
import com.example.myapp.model.response.BookResponse;
import com.example.myapp.model.response.PageResponse;
import com.example.myapp.repository.AuthorRepository;
import com.example.myapp.repository.BookRepository;
import com.example.myapp.repository.CategoryRepository;
import com.example.myapp.service.AdminBookService;
import com.example.myapp.util.BookMapper;
import com.example.myapp.util.PageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminBookServiceImpl implements AdminBookService {

        private final BookRepository bookRepository;
        private final AuthorRepository authorRepository;
        private final CategoryRepository categoryRepository;

        @Override
        public PageResponse<BookResponse> getBooks(int page, int size) {
                Pageable pageable = PageRequest.of(page, size);
                Page<Book> bookPage = bookRepository.findAll(pageable);
                return PageMapper.toPageResponse(bookPage, BookMapper::toResponse);
        }

        @Override
        public BookResponse createBook(BookRequest request) {
                Author author = authorRepository.findById(request.getAuthorId())
                                .orElseThrow(() -> new IllegalArgumentException("Author not found"));
                Category category = categoryRepository.findById(request.getCategoryId())
                                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

                Book book = new Book();
                book.setTitle(request.getTitle());
                book.setDescription(request.getDescription());
                book.setPrice(request.getPrice());
                book.setImageUrl(request.getImageUrl());
                book.setPublisher(request.getPublisher());
                book.setPublicationYear(request.getPublicationYear());
                book.setDimensions(request.getDimensions());
                book.setAuthor(author);
                book.setCategory(category);

                book = bookRepository.save(book);
                return BookMapper.toResponse(book);
        }

        @Override
        public BookResponse updateBook(Long bookId, BookRequest request) {
                Book book = bookRepository.findById(bookId)
                                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

                Author author = authorRepository.findById(request.getAuthorId())
                                .orElseThrow(() -> new IllegalArgumentException("Author not found"));
                Category category = categoryRepository.findById(request.getCategoryId())
                                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

                book.setTitle(request.getTitle());
                book.setDescription(request.getDescription());
                book.setPrice(request.getPrice());
                book.setImageUrl(request.getImageUrl());
                book.setPublisher(request.getPublisher());
                book.setPublicationYear(request.getPublicationYear());
                book.setDimensions(request.getDimensions());
                book.setAuthor(author);
                book.setCategory(category);

                book = bookRepository.save(book);
                return BookMapper.toResponse(book);
        }

        @Override
        public void deleteBook(Long bookId) {
                Book book = bookRepository.findById(bookId)
                                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
                bookRepository.delete(book);
        }

        @Override
        public PageResponse<BookResponse> searchBooks(String keyword, int page, int size) {
                Pageable pageable = PageRequest.of(page, size);
                Page<Book> bookPage = bookRepository.findByTitleContaining(keyword, pageable);
                return PageMapper.toBookPageResponse(bookPage);
        }
}
