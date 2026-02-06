package com.example.myapp.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myapp.entity.PasswordResetToken;
import com.example.myapp.entity.RefreshToken;
import com.example.myapp.entity.Role;
import com.example.myapp.entity.User;
import com.example.myapp.exception.InvalidCredentialsException;
import com.example.myapp.exception.InvalidRefreshTokenException;
import com.example.myapp.exception.InvalidTokenException;
import com.example.myapp.exception.UserAlreadyExistsException;
import com.example.myapp.exception.UserNotFoundException;
import com.example.myapp.model.request.ForgotPasswordRequest;
import com.example.myapp.model.request.LoginRequest;
import com.example.myapp.model.request.RefreshTokenRequest;
import com.example.myapp.model.request.RegisterRequest;
import com.example.myapp.model.request.ResetPasswordRequest;
import com.example.myapp.model.response.LoginResponse;
import com.example.myapp.model.response.RefreshTokenResponse;
import com.example.myapp.repository.PasswordResetTokenRepository;
import com.example.myapp.repository.RefreshTokenRepository;
import com.example.myapp.repository.RoleRepository;
import com.example.myapp.repository.UserfrofileRepository;
import com.example.myapp.security.JwtService;
import com.example.myapp.service.AuthService;
import com.example.myapp.service.EmailService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {
        final UserfrofileRepository userRepository;
        final RefreshTokenRepository refreshTokenRepository;
        final PasswordEncoder passwordEncoder;
        final JwtService jwtService;
        final RoleRepository roleRepository;
        final EmailService emailService;
        final PasswordResetTokenRepository resetTokenRepository;

        @Override
        public void registerUser(RegisterRequest request) {

                if (userRepository.existsByEmail(request.getEmail())) {
                        throw new UserAlreadyExistsException();
                }

                Role roleUser = roleRepository.findById("USER")
                                .orElseThrow(() -> new RuntimeException("Role USER chưa tồn tại"));

                User user = User.builder()
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .fullName(request.getFulltName())
                                .roles(Set.of(roleUser))
                                .build();

                userRepository.save(user);
        }

        @Override
        public LoginResponse login(LoginRequest request) {

                // 1️⃣ Tìm user theo email
                User user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(UserNotFoundException::new);

                // 2️⃣ Check password
                if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        throw new InvalidCredentialsException();
                }

                // 3️⃣ Lấy roles
                Set<String> roles = user.getRoles()
                                .stream()
                                .map(Role::getName)
                                .collect(Collectors.toSet());

                // 4️⃣ Generate JWT
                String accessToken = jwtService.generateToken(user);

                // 5️⃣ Tạo refresh token
                RefreshToken refreshToken = RefreshToken.builder()
                                .token(UUID.randomUUID().toString())
                                .user(user)
                                .expiryTime(Instant.now().plus(7, ChronoUnit.DAYS))
                                .build();

                refreshTokenRepository.save(refreshToken);

                // 6️⃣ Trả response
                return LoginResponse.builder()
                                .accessToken(accessToken)
                                .refreshToken(refreshToken.getToken())
                                .userId(user.getId())
                                .email(user.getEmail())
                                .roles(roles)
                                .build();
        }

        @Override
        public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

                RefreshToken token = refreshTokenRepository
                                .findByToken(request.getToken())
                                .orElseThrow(InvalidRefreshTokenException::new);

                // if (token.getExpiryTime().isBefore(Instant.now())) {
                // throw new InvalidRefreshTokenException();
                // }
                if (token.getExpiryTime().isBefore(Instant.now())) {
                        refreshTokenRepository.delete(token);
                        throw new InvalidRefreshTokenException();
                }

                String newAccessToken = jwtService.generateToken(token.getUser());
                return new RefreshTokenResponse(newAccessToken);
        }

        @Override
        public void forgotPassword(ForgotPasswordRequest request) {

                User user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(UserNotFoundException::new);

                // Xoá token cũ nếu có
                resetTokenRepository.deleteByUserId(user.getId());

                String token = UUID.randomUUID().toString();

                PasswordResetToken resetToken = PasswordResetToken.builder()
                                .token(token)
                                .user(user)
                                .expiryTime(Instant.now().plus(15, ChronoUnit.MINUTES))
                                .build();

                resetTokenRepository.save(resetToken);

                String resetLink = "http://localhost:3000/reset-password?token=" + token;

                emailService.send(
                                user.getEmail(),
                                "Reset your password",
                                "Click here to reset password:\n" + resetLink);
        }

        @Override
        public void resetPassword(ResetPasswordRequest request) {

                PasswordResetToken token = resetTokenRepository
                                .findByToken(request.getToken())
                                .orElseThrow(InvalidTokenException::new);

                if (token.getExpiryTime().isBefore(Instant.now())) {
                        throw new InvalidTokenException();
                }

                User user = token.getUser();
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.save(user);

                resetTokenRepository.delete(token);
        }

}
