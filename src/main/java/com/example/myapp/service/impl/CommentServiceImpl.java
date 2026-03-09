package com.example.myapp.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.example.myapp.service.CommentService;
import com.example.myapp.util.UserMapper;
import com.example.myapp.util.BookMapper;
import com.example.myapp.util.CommentMapper;
import com.example.myapp.util.SecurityUtil;
import com.example.myapp.repository.CommentRepository;
import com.example.myapp.entity.Book;
import com.example.myapp.entity.Comment;
import com.example.myapp.entity.User;
import com.example.myapp.exception.AppException;
import com.example.myapp.exception.ErrorCode;
import com.example.myapp.model.request.CreateCommentRequest;
import com.example.myapp.model.response.CommentResponse;
import com.example.myapp.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

        private final UserMapper userMapper;
        private final BookMapper bookMapper;
        private final CommentRepository commentRepository;
        private final BookRepository bookRepository;
        private final CommentMapper commentMapper;

        @Override
        public CommentResponse createComment(Long bookId, CreateCommentRequest request) {
                User user = SecurityUtil.getCurrentUser();
                Book book = bookRepository.findById(bookId)
                                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
                Comment comment = commentRepository.save(commentMapper.toEntity(request, user, book));
                return commentMapper.toResponse(comment);
        }

        @Override
        public List<CommentResponse> getCommentsByBook(Long bookId) {
                Book book = bookRepository.findById(bookId)
                                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
                List<Comment> comments = commentRepository.findByBook(book);
                return comments.stream()
                                .map(commentMapper::toResponse)
                                .collect(Collectors.toList());
        }

        @Override
        public void deleteComment(Long commentId) {
                Comment comment = commentRepository.findById(commentId)
                                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
                commentRepository.delete(comment);
        }

}
