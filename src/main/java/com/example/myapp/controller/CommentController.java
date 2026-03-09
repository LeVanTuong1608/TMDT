package com.example.myapp.controller;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.myapp.model.request.CreateCommentRequest;
import com.example.myapp.model.response.CommentResponse;
import com.example.myapp.model.response.PageResponse;
import com.example.myapp.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/book/{id}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping // api về danh sách comment của một book
    public ResponseEntity<List<CommentResponse>> getCommentsByBook(@PathVariable Long bookId) {
        List<CommentResponse> comments = commentService.getCommentsByBook(bookId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long bookId,
            @Valid @RequestBody CreateCommentRequest request) {
        CommentResponse comment = commentService.createComment(bookId, request);
        return ResponseEntity.status(HttpStatus.SC_CREATED)
                .body(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

}