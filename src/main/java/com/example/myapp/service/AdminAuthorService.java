package com.example.myapp.service;

import com.example.myapp.model.request.AuthorRequest;
import com.example.myapp.model.response.*;

public interface AdminAuthorService {
    PageResponse<AuthorResponse> getAuthors(int page, int size);

    AuthorResponse createAuthor(AuthorRequest request);

    AuthorResponse updateAuthor(Long id, AuthorRequest request);

    void deleteAuthor(Long id);

    PageResponse<AuthorResponse> searchAuthors(String keyword, int page, int size);

    // AuthorResponse getDetail(Long id);

}
