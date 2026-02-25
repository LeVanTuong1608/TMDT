package com.example.myapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myapp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Page<User> findAll(Pageable pageable);

    Page<User> findByFullNameContaining(String keyword, Pageable pageable);

    Page<User> findByEmailContaining(String keyword, Pageable pageable);

    Page<User> findByPhoneContaining(String keyword, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByFullName(String fullName);

}
