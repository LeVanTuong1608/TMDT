package com.example.myapp.service.impl;

import com.example.myapp.entity.Book;
import com.example.myapp.entity.Comment;
import com.example.myapp.entity.User;
import com.example.myapp.exception.AppException;
import com.example.myapp.exception.ErrorCode;
import com.example.myapp.exception.ResourceNotFoundException;
import com.example.myapp.model.response.CommentResponse;
import com.example.myapp.repository.BookRepository;
import com.example.myapp.repository.CommentRepository;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.service.CommentService;
import com.example.myapp.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

        private final CommentRepository commentRepository;
        private final BookRepository bookRepository;
        private final UserRepository userRepository;

        @Override
        public CommentResponse createComment(Long bookId, String content) {
                String userEmail = SecurityUtil.getCurrentUserLogin();
                User user = userRepository.findByEmail(userEmail)
                                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                // new ResourceNotFoundException("User not found"));

                Book book = bookRepository.findById(bookId)
                                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
                // new ResourceNotFoundException("Book not found"));

                Comment comment = new Comment();
                comment.setContent(content);
                comment.setUser(user);
                comment.setBook(book);
                comment.setCreatedAt(LocalDateTime.now());

                comment = commentRepository.save(comment);

                return mapToResponse(comment);
        }

        @Override
        public List<CommentResponse> getCommentsByBook(Long bookId) {
                Book book = bookRepository.findById(bookId)
                                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
                // new ResourceNotFoundException("Book not found"));

                List<Comment> comments = commentRepository.findByBook(book);
                return comments.stream()
                                .map(this::mapToResponse)
                                .collect(Collectors.toList());
        }

        @Override
        public void deleteComment(Long commentId) {
                String userEmail = SecurityUtil.getCurrentUserLogin();
                User user = userRepository.findByEmail(userEmail)
                                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                // new ResourceNotFoundException("User not found"));

                Comment comment = commentRepository.findById(commentId)
                                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
                // new ResourceNotFoundException("Comment not found"));

                if (!comment.getUser().getId().equals(user.getId())) {
                        throw new IllegalStateException("Access denied");
                }

                commentRepository.delete(comment);
        }

        private CommentResponse mapToResponse(Comment comment) {
                return CommentResponse.builder()
                                .id(comment.getId())
                                .content(comment.getContent())
                                .userEmail(comment.getUser().getEmail())
                                .bookId(comment.getBook().getId())
                                .createdAt(comment.getCreatedAt())
                                .build();
        }
}
