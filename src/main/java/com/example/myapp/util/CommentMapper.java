package com.example.myapp.util;

import org.springframework.stereotype.Component;

import com.example.myapp.entity.Comment;
import com.example.myapp.model.response.CommentResponse;
import com.example.myapp.model.request.CreateCommentRequest;
import com.example.myapp.entity.User;
import com.example.myapp.entity.Book;


@Component
public class CommentMapper {

    public Comment toEntity(CreateCommentRequest request, User user, Book book) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUser(user);
        comment.setBook(book);
        return comment;
    }

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
