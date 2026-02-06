package com.example.myapp.util;

import org.springframework.stereotype.Component;

import com.example.myapp.entity.Comment;
import com.example.myapp.model.response.CommentResponse;

@Component
public class CommentMapper {

    public CommentResponse toResponse(Comment comment) {

        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userEmail(comment.getUser().getEmail())
                .bookId(comment.getBook().getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
