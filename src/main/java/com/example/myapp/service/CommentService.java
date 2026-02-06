package com.example.myapp.service;

import com.example.myapp.model.response.CommentResponse;
import java.util.List;

public interface CommentService {
    CommentResponse addComment(Long bookId, String content);

    List<CommentResponse> getCommentsByBook(Long bookId);

    void deleteComment(Long commentId);
}
