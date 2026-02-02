package com.example.myapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myapp.entity.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository
        extends JpaRepository<PasswordResetToken, String> {

    Optional<PasswordResetToken> findByToken(String token);

    void deleteByUserId(Long userId);
}
