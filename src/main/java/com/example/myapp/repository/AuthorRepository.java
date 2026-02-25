package com.example.myapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.entity.Author;
// import com.example.myapp.entity.Book;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByName(String name);

    Page<Author> findByNameContaining(String keyword, Pageable pageable);

}
