package com.example.myapp.service;

import com.example.myapp.model.response.CommentResponse;
import com.example.myapp.model.request.CreateCommentRequest;
import java.util.List;

public interface CommentService {
    CommentResponse createComment(Long bookId, CreateCommentRequest request);

    List<CommentResponse> getCommentsByBook(Long bookId);

    void deleteComment(Long commentId);

}
