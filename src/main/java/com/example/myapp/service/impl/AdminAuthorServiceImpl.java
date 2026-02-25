package com.example.myapp.service.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


import com.example.myapp.entity.Author;
import com.example.myapp.exception.AppException;
import com.example.myapp.exception.ErrorCode;
import com.example.myapp.model.request.AuthorRequest;
import com.example.myapp.model.response.AuthorResponse;
import com.example.myapp.model.response.PageResponse;
import com.example.myapp.repository.AuthorRepository;
import com.example.myapp.service.AdminAuthorService;
import com.example.myapp.util.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminAuthorServiceImpl implements AdminAuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final PageMapper pageMapper;

    @Override
    public PageResponse<AuthorResponse> getAuthors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authorPage = authorRepository.findAll(pageable);
        return pageMapper.toPageResponse(authorPage, authorMapper::toResponse);
    }

    @Override
    public AuthorResponse createAuthor(AuthorRequest request) {
        Author author = new Author();
        author.setName(request.getName());
        author.setBirth(request.getBirth());
        // author.setImageUrl(request.getImageUrl());

        author = authorRepository.save(author);
        return authorMapper.toResponse(author);
    }

    @Override
    public AuthorResponse updateAuthor(Long id, AuthorRequest request) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));

        author.setName(request.getName());
        author.setBirth(request.getBirth());
        // author.setBiography(request.getBiography());
        // author.setImageUrl(request.getImageUrl());

        author = authorRepository.save(author);
        return authorMapper.toResponse(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
        authorRepository.delete(author);
    }

    @Override
    public PageResponse<AuthorResponse> searchAuthors(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authorPage = authorRepository.findByNameContaining(keyword, pageable);
        return pageMapper.toPageResponse(authorPage, authorMapper::toResponse);
    }


}
