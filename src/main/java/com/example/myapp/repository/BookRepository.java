package com.example.myapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myapp.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findAll(Pageable pageable);

    Page<Book> findByCategory_Id(Long categoryId, Pageable pageable);

    Page<Book> findByAuthor_Id(Long authorId, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}
