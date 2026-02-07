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

    @GetMapping
    public ResponseEntity<PageResponse<CommentResponse>> getComment(
            @PathVariable Long bookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                commentService.getCommentsByBook(bookId, page, size));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long bookId,
            @Valid @RequestBody CreateCommentRequest request) {

        return ResponseEntity.status(HttpStatus.SC_CREATED)
                .body(commentService.createComment(bookId, request));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommentResponse> deleteComment(
            @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    // @PostMapping("/book/{bookId}")
    // public ResponseEntity<CommentResponse> addComment(
    // @PathVariable Long bookId,
    // @RequestParam String content) {
    // return ResponseEntity.ok(commentService.addComment(bookId, content));
    // }

    // @GetMapping("/book/{bookId}")
    // public ResponseEntity<List<CommentResponse>> getCommentsByBook(@PathVariable
    // Long bookId) {
    // return ResponseEntity.ok(commentService.getCommentsByBook(bookId));
    // }

    // @DeleteMapping("/{commentId}")
    // public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
    // commentService.deleteComment(commentId);
    // return ResponseEntity.ok().build();
    // }
}
