package com.example.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myapp.entity.*;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    boolean existsByAuthorName(String authorName);
}
