package com.example.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myapp.entity.Comment;
import com.example.myapp.entity.Book;
import com.example.myapp.entity.User;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBook(Book book);

    List<Comment> findByUser(User user);
}
