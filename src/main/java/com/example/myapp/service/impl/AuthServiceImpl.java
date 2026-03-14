package com.example.myapp.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.entity.PasswordResetToken;
import com.example.myapp.entity.RefreshToken;
import com.example.myapp.entity.Role;
import com.example.myapp.entity.User;
import com.example.myapp.exception.AppException;
import com.example.myapp.exception.ErrorCode;
import com.example.myapp.exception.InvalidCredentialsException;
import com.example.myapp.exception.InvalidPasswordException;
import com.example.myapp.exception.InvalidRefreshTokenException;
import com.example.myapp.exception.InvalidTokenException;
import com.example.myapp.exception.UserNotFoundException;
import com.example.myapp.model.request.ForgotPasswordRequest;
import com.example.myapp.model.request.LoginRequest;
import com.example.myapp.model.request.RefreshTokenRequest;
import com.example.myapp.model.request.RegisterRequest;
import com.example.myapp.model.request.ResetPasswordRequest;
import com.example.myapp.model.request.UpdateUserRequest;
import com.example.myapp.model.response.LoginResponse;
import com.example.myapp.model.response.RefreshTokenResponse;
import com.example.myapp.model.response.UserResponse;
import com.example.myapp.repository.PasswordResetTokenRepository;
import com.example.myapp.repository.RefreshTokenRepository;
import com.example.myapp.repository.RoleRepository;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.security.JwtService;
import com.example.myapp.service.AuthService;
import com.example.myapp.service.EmailService;
import com.example.myapp.util.UserMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {
        final UserRepository userRepository;
        final RefreshTokenRepository refreshTokenRepository;
        final PasswordEncoder passwordEncoder;
        final JwtService jwtService;
        final RoleRepository roleRepository;
        final EmailService emailService;
        final PasswordResetTokenRepository resetTokenRepository;
        final UserMapper userMapper;

        @Override
        public void registerUser(RegisterRequest request) {

                if (userRepository.existsByEmail(request.getEmail())) {
                        throw new AppException(ErrorCode.EMAIL_EXITS);
                }

                Role roleUser = roleRepository.findById("ROLE_USER")
                                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

                User user = User.builder()
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .fullName(request.getFullName())
                                .phone(request.getPhone())
                                .roles(Set.of(roleUser))
                                .build();

                userRepository.save(user);
        }

        @Override
        public void registerUserAdmin(RegisterRequest request) {
                if (userRepository.existsByEmail(request.getEmail())) {
                        throw new AppException(ErrorCode.EMAIL_EXITS);
                }

                Role roleUser = roleRepository.findById("ROLE_ADMIN")
                                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

                User user = User.builder()
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .fullName(request.getFullName())
                                .phone(request.getPhone())
                                .roles(Set.of(roleUser))
                                .build();

                userRepository.save(user);
        }

        @Override
        public LoginResponse login(LoginRequest request) {

                User user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(UserNotFoundException::new);

                if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        throw new InvalidCredentialsException();
                }

                Set<String> roles = user.getRoles()
                                .stream()
                                .map(Role::getName)
                                .collect(Collectors.toSet());

                String accessToken = jwtService.generateToken(user);

                RefreshToken refreshToken = RefreshToken.builder()
                                .token(UUID.randomUUID().toString())
                                .user(user)
                                .expiryTime(Instant.now().plus(7, ChronoUnit.DAYS))
                                .build();

                refreshTokenRepository.save(refreshToken);

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

                if (token.getExpiryTime().isBefore(Instant.now())) {
                        refreshTokenRepository.delete(token);
                        throw new InvalidRefreshTokenException();
                }

                String newAccessToken = jwtService.generateToken(token.getUser());
                return new RefreshTokenResponse(newAccessToken);
        }

        @Transactional
        public void forgotPassword(ForgotPasswordRequest request) {

                Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

                if (userOpt.isPresent()) {

                        User user = userOpt.get();

                        resetTokenRepository.deleteByUser_Id(user.getId());

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
                                        "Click here:\n" + resetLink);
                }

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

        @Override
        public void logout(RefreshTokenRequest request) {
                RefreshToken token = refreshTokenRepository
                                .findByToken(request.getToken())
                                .orElseThrow(InvalidRefreshTokenException::new);

                refreshTokenRepository.delete(token);
        }

        @Override
        public void verifyEmail(String token) {
                // Dummy - implement email verification token logic if needed
        }

        @Override
        public void resendVerificationEmail(String email) {
                User user = userRepository.findByEmail(email)
                                .orElseThrow(UserNotFoundException::new);
                // emailService.sendVerificationEmail(user); // Uncomment when ready
        }

        @Override
        public void changePassword(String oldPassword, String newPassword) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String username = auth.getName();
                User user = userRepository.findByEmail(username)
                                .orElseThrow(UserNotFoundException::new);
                if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                        throw new InvalidPasswordException();
                }
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
        }

        @Override
        public UserResponse getMyProfile() {

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String username = auth.getName();
                User user = userRepository.findByEmail(username)
                                .orElseThrow(UserNotFoundException::new);
                return userMapper.toResponse(user);
        }

        @Override
        public UserResponse updateMyProfile(UpdateUserRequest request) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String username = auth.getName();
                User user = userRepository.findByEmail(username)
                                .orElseThrow(UserNotFoundException::new);
                user.setFullName(request.getFullName());
                user.setPhone(request.getPhone());
                user.setAddress(request.getAddress());
                user.setImageUrl(request.getImageUrl());
                user = userRepository.save(user);
                return userMapper.toResponse(user);
        }

}
