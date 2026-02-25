package com.example.myapp.service.impl;

import com.example.myapp.entity.Author;
import com.example.myapp.entity.Book;
import com.example.myapp.entity.Category;
import com.example.myapp.exception.*;
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
        private final BookMapper bookMapper;
        private final PageMapper pageMapper;

        @Override
        public PageResponse<BookResponse> getBooks(int page, int size) {
                Pageable pageable = PageRequest.of(page, size);
                Page<Book> bookPage = bookRepository.findAll(pageable);
                return pageMapper.toPageResponse(bookPage, bookMapper::toResponse);
        }

        // tạo mới sách
        @Override
        public BookResponse createBook(BookRequest request) {
                Author author = authorRepository.findById(request.getAuthorId())
                                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
                Category category = categoryRepository.findById(request.getCategoryId())
                                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

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
                return bookMapper.toResponse(book);
        }

        @Override
        public BookResponse updateBook(Long bookId, BookRequest request) {
                Book book = bookRepository.findById(bookId)
                                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

                Author author = authorRepository.findById(request.getAuthorId())
                                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
                Category category = categoryRepository.findById(request.getCategoryId())
                                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

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
                return bookMapper.toResponse(book);
        }

        @Override
        public void deleteBook(Long bookId) {
                Book book = bookRepository.findById(bookId)
                                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
                bookRepository.delete(book);
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
