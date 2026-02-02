package com.example.myapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.myapp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query(value = "SELECT * " +
            "FROM users u WHERE u.full_name LIKE CONCAT('%',?1,'%') " +
            "AND u.phone LIKE CONCAT('%',?2,'%') " +
            "AND u.email LIKE CONCAT('%',?3,'%') ", nativeQuery = true)
    Page<User> adminListUserPages(String fullName, String phone, String email, Pageable pageable);

}
