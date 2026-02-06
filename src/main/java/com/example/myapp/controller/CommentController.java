package com.example.myapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.myapp.model.response.CommentResponse;
import com.example.myapp.service.CommentService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/book/{bookId}")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long bookId,
            @RequestParam String content) {
        return ResponseEntity.ok(commentService.addComment(bookId, content));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(commentService.getCommentsByBook(bookId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
